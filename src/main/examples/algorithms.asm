        .org %c000

        srp     #%20
        ld      r0, #%00
        ld      r1, #%00
        ld      r2, #%FF
        ld      r3, #%FF
        ld      r4, #1
        jr      printDecU32

        ; in: r0-r3
        ;     r11 = trim leading zero
        ; destroy: r0-r11
printDecI32:
        or      r0, r0
        jr      mi, .neg
        or      r11, r11
        jr      z, printDecU32
        ld      %15, #' '
        jr      .2
.neg:   com     r0
        com     r1
        com     r2
        com     r3
        add     r3, #1
        adc     r2, #0
        adc     r1, #0
        adc     r0, #0
        ld      %15, #'-'
.2:     call    %0818

        ; in: r0-r3
        ;     r4 = trim leading zero (bool)
        ; destroy: r0-r11
printDecU32:
        ; result
        clr     r11
        clr     r12
        clr     r13
        clr     r14
        clr     r15
        ; summand (bcd-shifted power of 2)
        clr     r6
        clr     r7
        clr     r8
        clr     r9
        ld      r10, #1
        ; counter
        ld      r5, #%20      ; bit count
.1:     sra     r0
        rrc     r1
        rrc     r2
        rrc     r3
        jr      nc, .2
        add     r15, r10
        da      r15
        adc     r14, r9
        da      r14
        adc     r13, r8
        da      r13
        adc     r12, r7
        da      r12
        adc     r11, r6
        da      r11
.2:     add     r10, r10
        da      r10
        adc     r9, r9
        da      r9
        adc     r8, r8
        da      r8
        adc     r7, r7
        da      r7
        adc     r6, r6
        da      r6
        djnz    r5, .1

        ld      r6, #%2b     ; register
        ; counter
        ld      r7, #10
.loop:  ld      r5, @r6
        tm      r7, #1
        jr      nz, .4
        swap    r5
.4:     and     r5, #%0f
        or      r4, r4
        jr      z, .5
        cp      r7, #1
        jr      eq, .5
        or      r5, r5
        jr      z, .6
        clr     r4
.5:     ld      %15, r5
        add     %15, #'0'
        call    %0818
.6:     tm      r7, #1
        jr      z, .7
        inc     r6
.7:     djnz    r7, .loop
        ret
