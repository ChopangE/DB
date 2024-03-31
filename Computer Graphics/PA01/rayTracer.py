#!/usr/bin/env python3
# -*- coding: utf-8 -*
# sample_python aims to allow seamless integration with lua.
# see examples below

import os
import sys
import pdb  # use pdb.set_trace() for debugging
import code # or use code.interact(local=dict(globals(), **locals()))  for debugging.
import xml.etree.ElementTree as ET
import numpy as np
import math
from PIL import Image 
class Color:
    def __init__(self, R, G, B):
        self.color=np.array([R,G,B]).astype(np.float16)

    # Gamma corrects this color.
    # @param gamma the gamma value to use (2.2 is generally used).
    def gammaCorrect(self, gamma):
        inverseGamma = 1.0 / gamma
        self.color=np.power(self.color, inverseGamma)

    def toUINT8(self):
        return (np.clip(self.color, 0,1)*255).astype(np.uint8)

def main():

    
    tree = ET.parse(sys.argv[1])
    root = tree.getroot()

    # set default values
    viewDir=np.array([0,0,-1]).astype(np.float16)
    viewUp=np.array([0,1,0]).astype(np.float16)
    viewProjNormal=-1*viewDir  # you can safely assume this. (no examples will use shifted perspective camera)
    viewWidth=1.0
    viewHeight=1.0
    projDistance=1.0
    intensity=np.array([1,1,1]).astype(np.float16)  # how bright the light is.

    imgSize=np.array(root.findtext('image').split()).astype(np.int8)

    for c in root.findall('camera'):
        viewPoint=np.array(c.findtext('viewPoint').split()).astype(np.float16)
        viewDir = np.array(c.findtext('viewDir').split()).astype(np.float16)
        if (c.findtext('projNormal')):
            viewProjNormal = np.array(c.findtext('projNormal').split()).astype(np.float16)
        viewUp = np.array(c.findtext('viewUp').split()).astype(np.float16)
        if (c.findtext('projDistance')):
            projDistance = np.array(c.findtext('projDistance').split()).astype(np.float16)
        viewWidth = np.array(c.findtext('viewWidth').split()).astype(np.float16)
        viewHeight = np.array(c.findtext('viewHeight').split()).astype(np.float16)
    for c in root.findall('shader'):
        diffuseColor_c = np.array(c.findtext('diffuseColor').split()).astype(np.float16)
        specularColor = np.array(c.findtext('specularColor').split()).astype(np.float16)
        exponent = np.array(c.findtext('exponent').split()).astype(np.float16)
    for c in root.findall('surface'):
        center = np.array(c.findtext('center').split()).astype(np.float16)
        radius = np.array(c.findtext('radius').split()).astype(np.float16)
    for c in root.findall('light'):
        position = np.array(c.findtext('position').split()).astype(np.float16)
        intensity = np.array(c.findtext('intensity').split()).astype(np.float16)
    
    
    
    white=Color(1,1,1)
    red=Color(1,0,0)
    blue=Color(0,0,1)
    # Create an empty image
    channels=3
    img = np.zeros((imgSize[1], imgSize[0], channels), dtype=np.uint8)
    img[:,:]=0
    #imgSize[0], imgSize[1] = 300
    unitviewdir = viewDir/(math.sqrt(viewDir@viewDir))
    unit_viewProjNormal = viewProjNormal / (math.sqrt(viewProjNormal@viewProjNormal))
    imagecenterpoint = viewPoint + unitviewdir * projDistance
    u = np.cross( viewUp,unitviewdir)
    unit_u = u / math.sqrt(u@u)
    v = np.cross(unit_u, viewProjNormal)
    unit_v = v / math.sqrt(v@v)
    distance = math.sqrt(((viewPoint - center)@(viewPoint - center)))
    ratio = radius * projDistance / (distance)
    r_height = viewHeight * ratio
    r_width = viewWidth * ratio
    p = viewPoint - center                      #a는 구의 중점에서 viewPOint로 가는 vector이다. 이는  구의 중심벡터를 k라고 뒀을때 구와 p+td의 교점을 찾기위해서 (p+td-k) dot (p+td-k) = radius^2를 나타내기위해 p-k를 a로 뒀다.
    for i in np.arange(imgSize[1]):
        vec_u = -r_height/2+r_height/imgSize[1]*(i+0.5)    
        for j in np.arange(imgSize[0]): 
            vec_v = -r_width/2 +r_width/imgSize[0]*(j+0.5)
            d = vec_u*unit_u + vec_v*unit_v + imagecenterpoint - viewPoint
            root = (d@p) ** 2 - ((d@d) * (p@p-radius**2)) 
            if root >= 0 :    #hit
                h0 = (-d@p - math.sqrt(root))/(d@d)
                h1 = (-d@p + math.sqrt(root))/(d@d)
                if(h0>0 or h1>0):
                    t = h0
                    if(h0<0 and h1>0):
                        t = h1
                    Point = viewPoint + t * d
                    l = position - Point
                    unit_l = l / math.sqrt(l@l)
                    unit_n = (Point-center) / math.sqrt((Point-center)@(Point-center))
                    P_to_V = viewPoint - Point
                    unit_P_to_V = P_to_V / math.sqrt((P_to_V)@(P_to_V))
                    h = (unit_l + unit_P_to_V) /math.sqrt((unit_l + unit_P_to_V)@(unit_l + unit_P_to_V))
                    if(unit_l@h > 0):
                        cos2 = unit_n@h
                    else:
                        cos2 = 0
                    if(unit_l @ unit_n> 0):
                        cos = unit_l @ unit_n
                    else:
                        cos = 0
                    img[j][i] = (blue.toUINT8() * (diffuseColor_c * intensity * cos) + white.toUINT8() * ((specularColor * intensity * (cos2**exponent)))).astype(np.uint8) 
                    
                else:
                    img[j][i] = [50,50,50]
            else:                                           #make_shadow
                dirshadow = position - d
                p2 = d - center
                root2 = (dirshadow@p2)**2 - (dirshadow@dirshadow) * (p2@p2-(radius**2))
                if root2 >= 0:
                    img[j][i] = [0,0,0]
                else:
                    img[j][i]=[50,50,50]


    rawimg = Image.fromarray(img, 'RGB')
    #rawimg.save('out.png')
    rawimg.save(sys.argv[1]+'.png')
    
if __name__=="__main__":
    main()