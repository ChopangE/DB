import glfw
from OpenGL.GL import *
import numpy as np
th = np.radians(30)
M = np.identity(2)[0]
R = np.array([[np.cos(th),-np.sin(th)],[np.sin(th), np.cos(th)]])
k = 2
def key_callback(window, key, scancode, action, mods):
    global k
    if key==glfw.KEY_1:
        k = 0 
    elif key==glfw.KEY_2:
        k = 1  
    elif key==glfw.KEY_3:
        k = 2 
    elif key==glfw.KEY_4:
        k = 3             
    elif key==glfw.KEY_5:
        k = 4 
    elif key==glfw.KEY_6:
        k = 5
    elif key==glfw.KEY_7:
        k = 6
    elif key==glfw.KEY_8:
        k = 7
    elif key==glfw.KEY_9:
        k = 8 
    elif key==glfw.KEY_0:
        k = 9        
    
            
def render(M,R,k):
    glClear(GL_COLOR_BUFFER_BIT)
    glLoadIdentity()
    glBegin(k)
    a = M
    for i in range(12):
        glVertex2fv(a)
        a = R@a

    glEnd()
def main():
    global k
    if not glfw.init():
        return
    window = glfw.create_window(480,480,"2018009125-2-2", None,None)
    if not window:
        glfw.terminate()
        return
    glfw.set_key_callback(window, key_callback)
    glfw.make_context_current(window)
    glfw.swap_interval(1)
        
    while not glfw.window_should_close(window):
        render(M,R,k) 
        glfw.poll_events()
# Render here, e.g. using pyOpenGL
        
# Swap front and back buffers
        glfw.swap_buffers(window)
    glfw.terminate()
if __name__ == "__main__":
    main()
