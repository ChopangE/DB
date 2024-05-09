        lw  0   2   mcand
        lw  0   3   mplier
        lw  0   4   one
        lw  0   6   num
        lw  0   7   neg1
start   nor 0   4   5
        nor 3   5   5
        beq 0   5   adds
        add 2   2   2
        add 4   4   4
        add 6   7   6
        beq 0   6   done
        beq 0   0   start
adds    add 2   1   1
        add 2   2   2
        add 4   4   4
        add 6   7   6
        beq 0   6   done
        beq 0   0   start
done    halt
mcand   .fill   32766
mplier  .fill   12328
one     .fill   1
num     .fill   15
neg1    .fill   -1