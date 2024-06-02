        .org %c000

        srp     #%20
        ld      r12, #%00
        ld      r13, #%00
        ld      r14, #%FF
        ld      r15, #%FF
        ld      r11, #1
        jr      printDecU32

        ; in: r12-r15
        ;     r11 = trim leading zero
        ; destroy: r0-r11
printDecI32:
        or      r12, r12
        jr      mi, .1
        tm      r11, #1
        jr      z, printDecU32
        ld      %15, #' '
        jr      .2
.1:     com     r12
        com     r13
        com     r14
        com     r15
        add     r15, #1
        adc     r14, #0
        adc     r13, #0
        adc     r12, #0
        ld      %15, #'-'
.2:     call    %0818

        ; in: r12-r15
        ;     r11 = trim leading zero
        ; destroy: r0-r11
printDecU32:
        call    intToBcd

        ld      r6, #%20
        ld      r7, #10
.1:     ld      r5, @r6
        tm      r7, #1
        jr      nz, .2
        swap    r5
.2:     tm      r11, #1
        jr      z, .3
        cp      r7, #1
        jr      z, .3
        tm      r5, #%0f
        jr      z, .4
        and     r11, #%FE
.3:     call    printHexNibble
.4:     tm      r7, #1
        jr      z, .5
        inc     r6
.5:     djnz    r7, .1
        ret

        ; in: r5
printHexNibble:
        ld      %15, r5
        and     %15, #%0f
        cp      %15, #%0a
        jp      c, .1
        add     %15, #7
.1:     add     %15, #%30
        jp      %0818

        ; in: r12-r15
        ; out: r0-r4
        ; destroy: r5-r10
intToBcd:
        clr     r0
        clr     r1
        clr     r2
        clr     r3
        clr     r4
        clr     r5
        clr     r6
        clr     r7
        clr     r8
        ld      r9, #1
        ld      r10, #%20
.1:     sra     r12
        rrc     r13
        rrc     r14
        rrc     r15
        jr      nc, .2
        add     r4, r9
        da      r4
        adc     r3, r8
        da      r3
        adc     r2, r7
        da      r2
        adc     r1, r6
        da      r1
        adc     r0, r5
        da      r0
.2:     add     r9, r9
        da      r9
        adc     r8, r8
        da      r8
        adc     r7, r7
        da      r7
        adc     r6, r6
        da      r6
        adc     r5, r5
        da      r5
        djnz    r10, .1
        ret
