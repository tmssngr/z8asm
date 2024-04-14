        .const  SIO   = %F0
        .const  TMR   = %F1
        .const  T1    = %F2
        .const  PRE1  = %F3
        .const  T0    = %F4
        .const  PRE0  = %F5
        .const  P2M   = %F6
        .const  P3M   = %F7
        .const  P01M  = %F8
        .const  IPR   = %F9
        .const  IRQ   = %FA
        .const  IMR   = %FB
        .const  FLAGS = %FC
        .const  RP    = %FD
        .const  SPH   = %FE
        .const  SPL   = %FF

        .ORG    %0800

        JP      %F500
        JP      %F503
        JP      %F506
        JP      %F509
        JP      @%56    ; M_0830
        NOP
        JP      %F50F

M_0812: JP      M_0928

M_0815: JP      M_1300  ; getChar

M_0818: JP      M_1200
M_081B: JP      M_13B8
M_081E: JP      M_1360
M_0821: JP      M_14E4
M_0824: JP      M_14B4
M_0827: JP      M_17A5
M_082A: JP      M_0BF5
M_082D: JP      %FFFF

        ; Video-ISR
M_0830: OR      %3, #%80    ; P37 = 1, 10 cycles (2.5us)
        AND     %3, #%7F    ; P37 = 0, -"-
        DEC     %54         ; 6 cycles
        JR      Z, .1       ; 10/12 cycles
        IRET

.1:     LD      %56, SPH
        LD      %57, SPL
        INCW    %6E
        LD      SPH, #%F4   ; video-RAM from F400
        CLR     SPL
        LD      %54, #8     ; 10 cycles
.2:     DEC     %54         ; 6 cycles
        JR      NZ, .2      ; 10/12cycles
        JR      F, .3
.3:     LD      %54, #%C0   ; 10 cycles

.4:     OR      %3, #%80    ; P37 = 1, 10 cycles (2.5us)
        AND     %3, #%7F    ; P37 = 0, -"-
        TM      %6D, #%FF   ; 10 cycles
        JR      Z, .5       ; 10/12 cycles
        XOR     %3, #%40    ; 10 cycles
        JR      .6          ; 12 cycles

.5:     AND     %3, #%3F    ; P36 = P37 = 0; 10 cycles
        JR      F, .6       ; 10 cycles
.6:     JR      F, .7       ; -"-
.7:     NOP                 ; 6 cycles
        POP     %80         ; 16 times each 10 cycles, read F400-F40F
        POP     %80
        POP     %80
        POP     %80
        POP     %80
        POP     %80
        POP     %80
        POP     %80
        POP     %80
        POP     %80
        POP     %80
        POP     %80
        POP     %80
        POP     %80
        POP     %80
        POP     %80
        DEC     %54         ; 6 cycles
        JR      NZ, .4      ; 10/12 cycles
                            ; 192 loops a 256 cycles (64us)
        OR      %3, #%80    ; P37: 10 cycles H pulse (4us)
        AND     %3, #%7F
        LD      %54, #%51
        LD      SPH, %56
        LD      SPL, %57
        LD      %56, #8
        LD      %57, #%A9
        AND     IRQ, #%EF
        IRET

M_08A9: OR      %3, #%80
        AND     %3, #%7F
        DEC     %54
        JR      Z, M_08B4
        IRET

M_08B4: LD      %54, #6
        LD      %57, #%BB
        IRET

        OR      %3, #%80
        DEC     %54
        JR      Z, M_08C3
        IRET

M_08C3: AND     %3, #%7F
        LD      %54, #%20
        LD      %57, #%30
        IRET

        ; calculate video RAM address from screen position in %5B
        ; in : %5B: position
        ; out: rr4: address (F800...)
M_08CD: LD      R4, #%F0
        AND     R4, %5B
        SWAP    R4
        LD      R5, #%0F
        AND     R5, %5B
        RL      R5
        SRA     R4
        RRC     R5
        ADD     R4, #%F8
        RET

        NOP

        ; write char from %5A to screen position %5B
M_08E4: PUSH    RP
        SRP     #%60
        LD      R0, #%F4
        LD      R1, %5B
        LD      R2, %5A
        LDE     @RR0, R2
        CALL    M_08CD
        LD      R3, #0
        LD      R1, #5

.1:     RCF
        RRC     R2
        RRC     R3
        DJNZ    R1, .1

        ADD     R2, R7
        LD      R1, #8

.2:     LDC     R0, @RR2
        LDE     @RR4, R0
        ADD     R5, #%10    ; next pixel row
        INC     R3
        DJNZ    R1, .2

        POP     RP
        RET

        NOP

        ; init-Video-ISR
M_0910: SRP     #%F0
        LD      R11, #%10   ; IMR
        LD      R9, #8      ; IPR
        LD      R8, #%92    ; P01M
        LD      R7, #8      ; P3M
        LD      R5, #5      ; PRE0
        LD      R4, #%40    ; T0
        LD      R1, #3      ; TMR
        NOP
        NOP
        NOP
        NOP
        EI
        ; here the ISR is already executed the 1st time
        RET

        NOP
        NOP

        ; boot
M_0928: SRP     #%F0
        LD      R14, #%F7   ; SPH/SPL = %F700
        LD      R15, #0
        LD      R8, #%92    ; P01M
        LD      %6, #%E0    ; RR06 = E000
        CLR     %7
        CLR     %8
        SRP     #%50
        LD      R6, #8      ; RR56 = M_0830 (Video-ISR)
        LD      R7, #%30
        LD      R13, #0
        LD      %67, #%0F
        LD      R5, #%40
        CALL    M_0910      ; init-Video-ISR
        CALL    M_0AE0      ; print title
        NOP
        NOP
        NOP
        NOP
M_094E: LD      %55, #%40
        LD      %58, #%FF
        CALL    M_0A3B
        SRP     #%10
        CALL    M_0815
        CP      %13, #%0D
        JR      Z, M_094E
        LD      R6, %13
        CP      R6, #'?'
        JR      NZ, M_0989
        CALL    M_0815
        LD      R5, %13
        LD      R6, %13
        RL      %16
        LD      R2, %9E(R6)
        LD      R3, %9F(R6)
        CALL    M_0818
        LD      R5, #%3D
        CALL    M_0818
M_097F: CALL    M_0EE0
        LD      R5, #%0D
        CALL    M_0818
        JR      M_094E

M_0989: CP      R6, #%43
        JR      NZ, M_0998
        TM      %0F, #8
        JR      Z, M_09D2
        AND     %0F, #%F7
        JR      M_09B4
M_0998: LD      %0F, #4
        CLR     %0E
        LD      R0, %6
        LD      R1, %7
        CP      R6, #%4E
        JR      NZ, M_09AC
        LD      R4, #0
        LDE     @RR0, R4
M_09AA: JR      M_094E
M_09AC: CP      R6, #%52
        JR      NZ, M_09E2
        CALL    %06C9
M_09B4: CLR     %6D
        LD      %55, #%10
        LD      %58, #%FF
        CALL    %0738
        LD      R5, #%0D
        CALL    M_0818
        LD      R5, #%4F
        TM      %0F, #2
        JR      NZ, M_09D4
        LD      R5, #%53
        TM      %0F, #8
        JR      NZ, M_09D4
M_09D2: LD      R5, #%45
M_09D4: CALL    M_0818
        LD      R3, #%F0
        AND     %13, %0E
        SWAP    %13
        CLR     %12
        JR      M_097F
M_09E2: CP      R6, #%45
        JP      Z, M_0A80
        CP      R6, #%50
        JR      NZ, M_09F4
        CALL    M_0815
        LD      %5D, R3
        JR      M_09AA

M_09F4: NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        CP      R6, #%53
        JR      NZ, M_0A2C
        CALL    M_0EC5
        LD      %20, R0
        LD      %21, R1
        SUB     R3, R1
        SBC     R2, R0
        INCW    R2
        LD      %22, R2
        LD      %23, R3
        CALL    M_0821
M_0A29: JP      M_094E

M_0A2C: CP      R6, #%4C
        JP      NZ, M_0A45
        LD      %20, R0
        LD      %21, R1
        CALL    M_0824
        JR      M_0A29

M_0A3B: LD      %6C, #0
        LD      %15, #6
        CALL    M_0818
        RET

M_0A45: CP      R6, #%4D
        JP      NZ, M_0E09
        CALL    M_082A
        JP      M_094E

        NOP

M_0A52: LD      R12, #5
        LD      R4, #0
        LD      R5, #0
M_0A58: LD      R2, #0
        LD      R3, #0
        LD      R13, #%0A
M_0A5E: ADD     R3, R5
        ADC     R2, R4
        DJNZ    R13, M_0A5E
        LD      R5, %13
        LD      R4, %12
        LDE     R13, @RR14
        SUB     %1D, #%30
        JR      C, M_0A7E
        CP      %1D, #%0A
        JR      NC, M_0A7E
        ADD     R5, R13
        ADC     %14, #0
        INC     R15
        DJNZ    R12, M_0A58
        RCF
        RET
M_0A7E: SCF
        RET

M_0A80: LD      R15, #1
        LD      R14, #%F7
        CALL    M_0A52
        OR      R4, #%80
M_0A8A: LDE     R10, @RR0
        INCW    R0
        LDE     R11, @RR0
        DECW    R0
        OR      R10, R10
        JR      Z, M_0AB1
        CP      R10, R4
        JR      C, M_0AA2
        JR      UGT, M_0AB4
        CP      R11, R5
        JR      Z, M_0AB4
        JR      UGT, M_0AB4
M_0AA2: INCW    R0
M_0AA4: INCW    R0
        LDE     R10, @RR0
        CP      R10, #%0D
        JR      NZ, M_0AA4
        INCW    R0
        JR      M_0A8A

M_0AB1: JP      M_094E

M_0AB4: LDE     R2, @RR0
        TM      R2, #%80
        JR      Z, M_0AB1
        INCW    R0
        LDE     R3, @RR0
        AND     R2, #%7F
        CALL    M_0EE0
M_0AC5: INCW    R0
        LDE     R5, @RR0
        CALL    M_0818
        CP      R5, #%0D
        JR      NZ, M_0AC5
        CALL    M_081E
        CP      R3, #%20
        JR      NZ, M_0AB1
        INCW    R0
        JR      M_0AB4

        NOP
        NOP
        NOP

        ; print title
M_0AE0: SRP     #%10
        LD      R0, #%0A    ; rr0 = M_0AF0
        LD      R1, #%F0
.1:     LDE     R5, @RR0
        CALL    M_0818
        INC     R1
        JR      NZ, .1
        RET

        NOP

M_0AF0: .data %0c "ES2.3_SWB_1989\r"

M_0B00: .repeat %F5
            nop
        .end

M_0BF5: LD      %15, #5
        CALL    M_0818
        PUSH    RP
        SRP     #%60
        LD      R0, #%F7
        LD      R1, #%80
        LD      R2, #%10
        LD      R3, #%10
M_0C07: LDEI    @RR0, @R2
        DJNZ    R3, M_0C07
        SRP     #%10
M_0C0D: LD      %58, #%FF
        LD      %55, #%20
        CALL    M_0815
        LD      R15, #0
        LD      R14, #%F7
        LDE     R0, @RR14
        INC     R15
        LD      R12, #%0C
        LD      R13, #%39
        LD      R1, #%10
M_0C23: LDE     R2, @RR12
        INC     R13
        CP      R2, R0
        JR      NZ, M_0C33
        LDE     R0, @RR12
        INC     R13
        LDE     R1, @RR12
        CALL    @%10
        JR      M_0C0D

M_0C33: INC     R13
        INC     R13
        DJNZ    R1, M_0C23
        JR      M_0C0D

M_0C39: .data %46 %16 %40 %50 %0D %2D %48
        .data %0C %F2 %2C %0D %11 %4A %0D %52  %41 %0D %32 %3B %0D %74 %53 %0D
        .data %86 %4C %0D %97 %4D %0F %80 %3F  %0D %CC %51 %0C %EB %44 %0D %EB
        .data %2E %0D %F1 %45 %0D %F7 %FF %FF  %FF

M_0C69: PUSH    R9
        LD      %19, R8
        CALL    M_0C72
        POP     R9
M_0C72: PUSH    R9
        SWAP    R9
        CALL    M_0C7B
        POP     R9
M_0C7B: LD      R5, #%0F
        AND     R5, R9
        ADD     R5, #%30
        CP      R5, #%3A
        JR      C, M_0C8A
        ADD     R5, #7
M_0C8A: JP      M_0818
M_0C8D: LD      R5, #%0D
        JR      M_0C8A
M_0C91: CALL    M_0C8D
        CALL    M_081E
        CP      %13, #%20
        RET

M_0C9B: CALL    M_0818
        LD      %18, R10
        LD      %19, R11
        CALL    M_0C69
        LD      R5, #%20
        JR      M_0C8A
M_0CA9: CALL    M_0CD5
        JR      NC, M_0CB2
        POP     R0
        POP     R0
M_0CB2: LD      R10, R12
        LD      R11, R13
        INC     R15
        RET
M_0CB8: LDE     R13, @RR14
        INC     R15
        CP      R13, #%30
        JR      C, M_0CD4
        SUB     R13, #%30
        CP      R13, #%0A
        JR      C, M_0CD3
        CP      R13, #%11
        JR      C, M_0CD4
        SUB     R13, #7
        CP      R13, #%10
M_0CD3: CCF
M_0CD4: RET
M_0CD5: CALL    M_0CDC
        JR      C, M_0CEA
        LD      %1C, R13
M_0CDC: CALL    M_0CB8
        JR      C, M_0CEA
        LD      R2, R13
        SWAP    R2
        CALL    M_0CB8
        OR      R13, R2
M_0CEA: RET
        POP     R0
        POP     R0
        JP      M_0DB5
        CALL    M_0CA9
M_0CF5: LD      R5, #%2C
        CALL    M_0C9B
        LD      R0, #4
M_0CFC: LDE     R9, @RR10
        OR      R10, R10
        JR      NZ, M_0D04
        LD      R9, @R11
M_0D04: INCW    R10
        CALL    M_0C72
        DJNZ    R0, M_0CFC
        CALL    M_0C91
        JR      Z, M_0CF5
M_0D10: RET
        CALL    M_0CA9
        LD      R0, #4
M_0D16: CALL    M_0CDC
        JR      C, M_0D10
        OR      R10, R10
        JR      Z, %0D22                ;!!!
        LDE     @RR10, R13
        JP      F, %F3BD
        INCW    R10
        DJNZ    R0, M_0D16
        LD      R5, #%2C
M_0D2A: JP      M_0C9B
        LDE     R5, @RR14
        LD      %5D, R5
        RET
        CALL    M_0CA9
M_0D35: LD      R5, #%3B
        CALL    M_0C9B
        LD      R0, #8
M_0D3C: LDE     R5, @RR10
        TM      R5, #%F0
        JR      NZ, M_0D45
        LD      R5, #%20
M_0D45: INCW    R10
        CALL    M_0818
        DJNZ    R0, M_0D3C
        CALL    M_0C91
        JR      Z, M_0D35
        RET
        CALL    M_0CA9
        LD      FLAGS, R6
        LD      RP, %17
        CALL    @%1A
        LD      %17, RP
        SRP     #%10
        LD      R6, FLAGS
        RET

        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP

        CALL    M_0CA9
        LD      R0, #8
M_0D79: LDE     R2, @RR14
        LDE     @RR10, R2
        INC     R15
        INCW    R10
        DJNZ    R0, M_0D79
        LD      R5, #%3B
        JR      M_0D2A
        CALL    M_0CA9
        LD      %20, R10
        LD      %21, R11
        CALL    M_0CA9
        LD      %22, R10
        LD      %23, R11
        JP      M_0821
        CALL    M_0CA9
        LD      %20, R10
        LD      %21, R11
        CALL    M_0824
        LD      R8, %22
        LD      R9, %23
        CALL    M_0C69
        LD      R5, #%20
        CALL    M_0818
        LD      R9, %24
        CALL    M_0C72
M_0DB2: JP      M_0C8D

M_0DB5: SRP     #%60
        LD      R0, #%F7
        LD      R1, #%80
        LD      R2, #%10
        LD      R3, #%10
M_0DBF: LDEI    @R2, @RR0
        DJNZ    R3, M_0DBF
        POP     RP
        RET

        NOP
        NOP
        NOP
        NOP
        NOP
        NOP

        CALL    M_0CA9
        CALL    M_0CD5
        JR      C, M_0DEA
        LD      R8, R10
        LD      R9, R11
        ADD     R9, R13
        ADC     R8, R12
        CALL    M_0C69
        LD      R5, #%20
        SUB     R11, R13
        SBC     R10, R12
        CALL    M_0C9B
        JR      M_0DB2

M_0DEA: RET
        CALL    M_0CA9
        JP      %C100

        CALL    M_0CA9
        JP      %C103

        LDE     R3, @RR14
        INC     R15
        CALL    M_0CA9
        JP      %C106

        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP

M_0E09: CP      R6, #%3A
M_0E0C: JP      NC, M_094E
        CP      R6, #%30
        CCF
        JR      NC, M_0E0C
        LD      R15, #0
        LD      R14, #%F7
        CALL    M_0A52
        CCF
        JR      NC, M_0E0C
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        OR      R4, #%80
        CALL    M_0EC5
M_0E2E: LDE     R10, @RR0
        INCW    R0
        LDE     R11, @RR0
        DECW    R0
        OR      R10, R10
        JR      Z, M_0E80
        CP      R10, R4
        JR      C, M_0E46
        JR      UGT, M_0E80
        CP      R11, R5
        JR      Z, M_0E55
        JR      UGT, M_0E80
M_0E46: INCW    R0
M_0E48: INCW    R0
        LDE     R10, @RR0
        CP      R10, #%0D
        JR      NZ, M_0E48
        INCW    R0
        JR      M_0E2E

M_0E55: LD      R6, R0
        LD      R7, R1
        INCW    R6
M_0E5B: INCW    R6
        LDE     R10, @RR6
        CP      R10, #%0D
        JR      NZ, M_0E5B
        INCW    R6
        LD      R8, R0
        LD      R9, R1
M_0E6A: LDE     R10, @RR6
        LDE     @RR8, R10
        CP      R6, R2
        JR      NZ, M_0E76
        CP      R7, R3
        JR      Z, M_0E7C
M_0E76: INCW    R6
        INCW    R8
        JR      M_0E6A

M_0E7C: LD      R2, R8
        LD      R3, R9
M_0E80: LD      R15, #4
        LD      R14, %58
        CP      R14, R15
        JP      ULE, M_094E
        LD      R7, #%FF
        ADD     R7, R14
        LD      R6, #0
        ADD     R7, R3
        ADC     R6, R2
M_0E93: LDE     R10, @RR2
        LDE     @RR6, R10
        CP      R0, R2
        JR      NZ, M_0E9F
        CP      R1, R3
        JR      Z, M_0EA5
M_0E9F: DECW    R2
        DECW    R6
        JR      M_0E93

M_0EA5: LDE     @RR0, R4
        INCW    R0
        LDE     @RR0, R5
        INCW    R0
        LD      R2, #%F7
        LD      R3, #5
        SUB     R14, R15
M_0EB3: LDE     R10, @RR2
        LDE     @RR0, R10
        INCW    R0
        INC     R3
        DEC     %1E
        JR      NZ, M_0EB3
        LD      R10, #%0D
        LDE     @RR0, R10
        JP      M_094E

M_0EC5: LD      R2, R0
        LD      R3, R1
M_0EC9: LDE     R10, @RR2
        TM      R10, #%80
        JR      Z, M_0EDF
        INCW    R2
M_0ED2: INCW    R2
        LDE     R10, @RR2
        CP      R10, #%0D
        JR      NZ, M_0ED2
        INCW    R2
        JR      M_0EC9

M_0EDF: RET

M_0EE0: CALL    %0182
        CP      R4, #%20
        JR      Z, M_0EF1
        PUSH    %15
        LD      R5, R4
        CALL    M_0818
        POP     %15
M_0EF1: LD      R11, #%15
M_0EF3: LD      R5, @R11
        CALL    M_0818
        INC     R11
        CP      R11, #%1A
        JR      C, M_0EF3
        RET

        NOP

M_0F00: .data %FF "YXCVBNM,./\r  " %04 %0d
        .data %FF "ASDFGHJKL;+*" %01 %05 %02
        .data %FF "QWERTZUIOP-=" %07 %03 %06
        .data %FF "1234567890<>" %08 %09 %0C
        .data %FF %FF %FF %FF %FF %FF %FF %FF "[]?" %FF %FF %FF %FF %FF
        .data %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF ":\\^" %FF %FF %FF
        .data %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF "_" %FF %FF %FF %FF
        .data %FF "!" %22 "#$%&'@()" %FF %FF %FF %FF %FF

M_0F80: CALL    M_0CA9
        LD      R8, R10
        LD      R9, R11
        CALL    M_0CA9
        CALL    M_0CD5
        JR      C, M_0FAD
        CP      R8, R10
        JR      UGT, M_0FAE
        JR      C, M_0F99
        CP      R9, R11
        JR      NC, M_0FAE
M_0F99: ADD     R9, R13
        ADC     R8, R12
        ADD     R11, R13
        ADC     R10, R12
M_0FA1: DECW    R8
        DECW    R10
        LDE     R0, @RR8
M_0FA7: LDE     @RR10, R0
        DECW    R12
        JR      NZ, M_0FA1
M_0FAD: RET
M_0FAE: LDE     R0, @RR8
        LDE     @RR10, R0
        INCW    R8
        INCW    R10
        DECW    R12
M_0FB8: JR      NZ, M_0FAE
        RET

        .repeat %45
            .data %FF
        .end

M_1000: .data %FF %FF %FF %FF %FF %FF %FF %FF ; space
        .data %FF %E7 %E7 %E7 %E7 %FF %E7 %FF ; !
        .data %FF %99 %99 %99 %FF %FF %FF %FF ; "
        .data %FF %99 %00 %99 %99 %00 %99 %FF ; #
        .data %E7 %C1 %9F %C3 %F9 %83 %E7 %FF ; $
        .data %FF %99 %93 %E7 %CF %99 %B9 %FF ; %
        .data %E3 %C9 %E3 %C7 %90 %99 %C4 %FF ; &
        .data %FF %E7 %E7 %E7 %FF %FF %FF %FF ; '
        .data %FF %F1 %E3 %E7 %E7 %E3 %F1 %FF ; (
        .data %FF %8F %C7 %E7 %E7 %C7 %8F %FF ; )
        .data %FF %99 %C3 %00 %C3 %99 %FF %FF ; *
        .data %FF %E7 %E7 %81 %E7 %E7 %FF %FF ; +
        .data %FF %FF %FF %FF %FF %E7 %E7 %CF ; ,
        .data %FF %FF %FF %81 %FF %FF %FF %FF ; -
        .data %FF %FF %FF %FF %FF %E7 %E7 %FF ; .
        .data %FF %F9 %F3 %E7 %CF %9F %BF %FF ; /
        .data %FF %C3 %99 %91 %89 %99 %C3 %FF ; 0
        .data %FF %E7 %C7 %E7 %E7 %E7 %81 %FF ; 1
        .data %FF %C3 %99 %F3 %E7 %CF %81 %FF ; 2
        .data %FF %81 %F3 %E7 %F3 %99 %C3 %FF ; 3
        .data %FF %F3 %E3 %C3 %93 %81 %F3 %FF ; 4
        .data %FF %81 %9F %83 %F9 %99 %C3 %FF ; 5
        .data %FF %C3 %9F %83 %99 %99 %C3 %FF ; 6
        .data %FF %81 %F9 %F3 %E7 %CF %CF %FF ; 7
        .data %FF %C3 %99 %C3 %99 %99 %C3 %FF ; 8
        .data %FF %C3 %99 %C1 %F9 %F3 %C7 %FF ; 9
        .data %FF %FF %E7 %E7 %FF %E7 %E7 %FF ; :
        .data %FF %FF %E7 %E7 %FF %E7 %E7 %CF ; ;
        .data %F9 %F3 %E7 %CF %E7 %F3 %F9 %FF ; <
        .data %FF %FF %81 %FF %FF %81 %FF %FF ; =
        .data %9F %CF %E7 %F3 %E7 %CF %9F %FF ; >
        .data %FF %C3 %99 %F3 %E7 %FF %E7 %FF ; ?
        .data %FF %C3 %99 %91 %91 %9F %C1 %FF ; @
        .data %FF %E7 %C3 %99 %99 %81 %99 %FF ; A
        .data %FF %83 %99 %83 %99 %99 %83 %FF ; B
        .data %FF %C3 %99 %9F %9F %99 %C3 %FF ; C
        .data %FF %87 %93 %99 %99 %93 %87 %FF ; D
        .data %FF %81 %9F %83 %9F %9F %81 %FF ; E
        .data %FF %81 %9F %83 %9F %9F %9F %FF ; F
        .data %FF %C1 %9F %9F %91 %99 %C1 %FF ; G
        .data %FF %99 %99 %81 %99 %99 %99 %FF ; H
        .data %FF %81 %E7 %E7 %E7 %E7 %81 %FF ; I
        .data %FF %F9 %F9 %F9 %F9 %99 %C3 %FF ; J
        .data %FF %99 %93 %87 %87 %93 %99 %FF ; K
        .data %FF %9F %9F %9F %9F %9F %81 %FF ; L
        .data %FF %9C %88 %80 %94 %9C %9C %FF ; M
        .data %FF %99 %89 %81 %81 %91 %99 %FF ; N
        .data %FF %C3 %99 %99 %99 %99 %C3 %FF ; O
        .data %FF %83 %99 %99 %83 %9F %9F %FF ; P
        .data %FF %C3 %99 %99 %99 %93 %C9 %FF ; Q
        .data %FF %83 %99 %99 %83 %93 %99 %FF ; R
        .data %FF %C3 %9F %C3 %F9 %F9 %C3 %FF ; S
        .data %FF %81 %E7 %E7 %E7 %E7 %E7 %FF ; T
        .data %FF %99 %99 %99 %99 %99 %81 %FF ; U
        .data %FF %99 %99 %99 %99 %C3 %E7 %FF ; V
        .data %FF %9C %9C %94 %80 %88 %9C %FF ; W
        .data %FF %99 %99 %C3 %C3 %99 %99 %FF ; X
        .data %FF %99 %99 %C3 %E7 %E7 %E7 %FF ; Y
        .data %FF %81 %F3 %E7 %CF %9F %81 %FF ; Z
        .data %FF %E1 %E7 %E7 %E7 %E7 %E1 %FF ; [
        .data %FF %BF %9F %CF %E7 %F3 %F9 %FF ; \
        .data %FF %87 %E7 %E7 %E7 %E7 %87 %FF ; ]
        .data %FF %F7 %E3 %C9 %9C %FF %FF %FF ; ^
        .data %FF %FF %FF %FF %FF %FF %00 %FF ; _

        ; print char from %15
M_1200: CP      %5D, #%4F       ; 'O'
        JR      NZ, M_1208
        CALL    %F512
M_1208: LD      %5A, %15
M_120B: PUSH    RP
        SRP     #%50
        LD      R12, %55
        DEC     %5C
        TM      %5A, #%F0
        JR      Z, M_1263       ; handle control char
        CALL    M_08E4
        LD      R12, #1
M_121D: ADD     R11, R12
        JR      C, M_1224
        POP     RP
        RET

M_1224: SUB     R11, R5
        SRP     #%60
        LD      R0, #%F4
        LD      R2, #%F4
        LD      R1, %55
        LD      R3, #0
M_1230: LDE     R4, @RR0
        LDE     @RR2, R4
        INC     R3
        INC     R1
        JR      NZ, M_1230
        LD      R4, #%20
M_123A: LDE     @RR2, R4
        INC     R3
        JR      NZ, M_123A
        LD      R2, #%F8
        LD      R0, #%1F
        LD      R1, %55
        LD      R4, #3
        RCF
M_1248: RLC     R1
        RLC     R0
        DJNZ    R4, M_1248
M_124E: LDE     R4, @RR0
        LDE     @RR2, R4
        INCW    R2
        INCW    R0
        JR      NZ, M_124E
        LD      R4, #%FF
M_125A: LDE     @RR2, R4
        INCW    R2
        JR      NZ, M_125A
M_1260: POP     RP
        RET

        ; handle control char
M_1263: DJNZ    R10, M_1269
        DEC     %5B         ; 01
        JR      M_1260

M_1269: DJNZ    R10, M_126E
        INC     R11         ; 02
        JR      M_1260

M_126E: DJNZ    R10, M_1274
        SUB     R11, R5     ; 03
        JR      M_1260

M_1274: DJNZ    R10, M_127A
M_1276: LD      R12, %55    ; 04
        JR      M_121D

M_127A: DJNZ    R10, M_1280
        LD      R11, #0     ; 05
        JR      M_1260

M_1280: DJNZ    R10, M_1288
        COM     %5C         ; 06
        AND     R11, R12
        JR      M_1260

M_1288: DJNZ    R10, M_12A7
        PUSH    %5B         ; 07
        LD      R14, #%F4
        LD      R15, %5B
M_1290: TCM     R15, R12
        JR      Z, M_129D
        INC     R15
        LDE     R10, @RR14
        CALL    M_08E4
        INC     R11
        JR      M_1290

M_129D: LD      R10, #%20
        CALL    M_08E4
        POP     %5B
M_12A4: POP     RP
        RET

M_12A7: DJNZ    R10, M_12B6
        TM      R11, R12    ; 08
        JR      Z, M_12B4
        DEC     %5B
        LD      R10, #%20
        CALL    M_08E4
M_12B4: JR      M_12A4

M_12B6: DJNZ    R10, M_12DA
        TCM     R11, R12    ; 09
        JR      Z, M_12D3
        LD      R9, %5B
        LD      R14, #%F4
        LD      R15, %5B
        OR      R15, R12
        LD      R11, %5F
M_12C6: DEC     %5F
        LDE     R10, @RR14
        CALL    M_08E4
        DEC     %5B
        CP      R11, R9
        JR      NZ, M_12C6
M_12D3: LD      R10, #%20
        CALL    M_08E4
        JR      M_12A4

M_12DA: DJNZ    R10, M_12DE
        NOP                 ; 0a
        NOP
M_12DE: DJNZ    R10, M_12E2
        NOP                 ; 0b
        NOP
M_12E2: DJNZ    R10, M_12F0
        LD      R11, #0     ; 0c = CLS
.cls:   LD      R10, #%20
        CALL    M_08E4
        INC     R11
        JR      NZ, .cls
        JR      M_12A4

M_12F0: DJNZ    R10, M_12F9
        COM     %5C         ; 0d
        AND     R11, R12
        JP      M_1276
M_12F9: POP     RP
        RET

        NOP
        NOP
        NOP
        NOP

        ; read char
M_1300: PUSH    RP
        SRP     #%50
        OR      R8, R8
        JR      MI, M_131D
M_1308: DEC     %58
        JR      PL, M_1313
        LD      R12, #%0D
M_130E: LD      %13, R12
        POP     RP
        RET

M_1313: LD      R14, #%F7
        LD      R15, %66
        LDE     R12, @RR14
        INC     %66
        JR      M_130E

M_131D: CALL    M_081E
        LD      R10, %13
        CALL    M_120B
        CP      %13, #%0D
        JR      NZ, M_131D
        LD      R8, #%F4
        LD      R9, %5B
        SUB     R9, R5
        LD      R14, #%F7
        LD      R15, #0
        LD      R10, #0
        NOP
        NOP
        NOP
        NOP
M_133A: LDE     R12, @RR8
        LDE     @RR14, R12
        CP      %5C, #%20
        JR      Z, M_134E
        NOP
        NOP
        NOP
        NOP
        LD      R10, %5F
        INC     R10
        NOP
        NOP
        NOP
        NOP
M_134E: INC     R15
        INC     R9
        CP      R9, R11
        JR      NZ, M_133A
        NOP
        NOP
        LD      R8, %5A
        LD      %66, #0
        JR      M_1308

        NOP
        NOP
        NOP

M_1360: PUSH    RP
        SRP     #%60
        CALL    M_08CD      ; calculate video RAM address
        ADD     R5, #%70
        LDE     R0, @RR4
        PUSH    R0
        COM     R0
        LDE     @RR4, R0
        LD      %5F, R13
        .data   %0d         ; jp f = skip next
M_1375: LD      R12, #0
        CALL    M_081B
        LD      %13, R13
        OR      R13, R13
        JR      Z, M_1375   ; repeat until key pressed
        LD      R0, #6
        ADD     R0, R15
.1:     CP      R0, R15
        JR      NZ, .1      ; wait until r0==r15
        OR      R12, R12
        JR      Z, M_1399
        CP      %6D, %5F
        JR      Z, M_13B0
        LD      R0, #5
        ADD     R0, R15
M_1395: CP      R0, R15
        JR      NZ, M_1395
M_1399: LD      %5F, #%14
M_139C: LD      %5E, R15
        CALL    M_081B
        OR      R13, R13
        JR      Z, M_13B0
M_13A5: CP      %5E, %6F
        JR      Z, M_13A5
        DEC     %5F
        JR      NZ, M_139C
        LD      R12, #%FF
M_13B0: POP     R0
        LDE     @RR4, R0
        POP     RP
        RET

        NOP

        ; read key
        ; out: %6D
M_13B8: PUSH    RP
        SRP     #%60
        LD      R0, #%7F        ; rr0 = 7F0F (keyboard matrix)
        LD      R1, #%0F
.1:     LDC     R2, @RR0
        AND     R2, #%F0
        JR      NZ, M_13CE      ; -> key pressed
        DJNZ    R1, .1
        LD      R13, #0
M_13CB: POP     RP
        RET

        ; key pressed
M_13CE: LD      R3, #%40
M_13D0: SUB     R3, #%10
        RL      R2
        JR      NC, M_13D0
        OR      R1, R3
        LD      R2, #%7F
        LD      R3, #0
        LDC     R0, @RR2
        LD      R2, #%F7
        LD      R3, #%40
        ADD     R3, R1
        RL      %60
        RL      %60
        RL      %60
        JR      C, M_13F6
        LD      R2, #%0F
        RL      %60
        JR      C, M_13F6
        SUB     %63, #%40
M_13F6: LDC     R13, @RR2
        JR      M_13CB

        NOP
        NOP
        NOP
        NOP
        NOP
        NOP

M_1400: PUSH    RP
        SRP     #%60
        LD      R0, #%F7
        LD      R1, #%90
        LD      R2, #4
        LD      R3, #%0C
M_140C: LDEI    @RR0, @R2
        DJNZ    R3, M_140C
        POP     RP
        RET

        NOP

M_1414: PUSH    RP
        SRP     #%60
        LD      R0, #%F7
        LD      R1, #%90
        LD      R2, #4
        LD      R3, #%0C
M_1420: LDEI    @R2, @RR0
        DJNZ    R3, M_1420
        POP     RP
        RET

        NOP

M_1428: DI
        LD      %5E, #%FF
        LD      P01M, #%92
        LD      T0, #%64
        LD      PRE0, #5
        LD      TMR, #%43
        LD      P3M, #8
        RET

        NOP

M_143D: CALL    M_1428
        LD      %0D, #0
        LD      TMR, #3
        RET

        NOP

M_1448: CALL    M_1428
        PUSH    RP
        SRP     #0
        LD      R4, #0
        LD      R5, #0
M_1453: LD      R6, #%18
M_1455: DJNZ    R6, M_1455
        DECW    %4
        JR      NZ, M_1453
        LD      R13, #%80
        POP     RP
        RET

M_1460: PUSH    RP
        SRP     #0
        OR      R13, R13
        JR      NZ, M_146F
        CALL    M_1580
        JR      C, M_1479
        LD      R13, #%80
M_146F: LDE     R15, @RR12
        INC     R13
        CP      R13, R11
        JR      NZ, M_1478
        LD      R13, #0
M_1478: RCF
M_1479: POP     RP
        RET

        NOP
        NOP

M_147E: PUSH    RP
        SRP     #0
        LD      R12, #%F5
        LDE     @RR12, R15
        INC     R13
        JR      NZ, M_1490
        LD      R11, #%FC
        CALL    M_1500
        LD      R13, #%80
M_1490: POP     RP
        RET

        NOP

M_1494: CP      %0D, #%80
        JR      Z, M_149F
        LD      %0B, #%FA
        CALL    M_1500
M_149F: LD      %0B, #%FE
        CALL    M_1500
M_14A5: CALL    M_0910
        RET

        NOP

M_14AA: LD      %60, #%18
M_14AD: DEC     %60
        JR      NZ, M_14AD
        RET

        NOP
        NOP

M_14B4: CALL    M_1400
        CALL    M_143D
        PUSH    RP
        SRP     #%20
        LD      R2, #0
        LD      R3, #0
        LD      R4, #0
M_14C4: CALL    M_1460
        JR      C, M_14D3
        LD      R5, %0F
        LDE     @RR0, R5
        INCW    %20
        INCW    %22
        JR      M_14C4

M_14D3: CP      %0E, #%88
        JR      Z, M_14DA
        LD      R4, %0E
M_14DA: CALL    M_14A5
M_14DD: CALL    M_1414
        POP     RP
        RET

        NOP

M_14E4: CALL    M_1400
        CALL    M_1448
        PUSH    RP
        SRP     #%20
M_14EE: LDE     R5, @RR0
        LD      %0F, R5
        CALL    M_147E
        INCW    %20
        DECW    %22
        JR      NZ, M_14EE
        CALL    M_1494
        JR      M_14DD

M_1500: PUSH    RP
        SRP     #0
        LD      R4, #%90
        LD      R5, #0
M_1508: DECW    %4
        JR      NZ, M_1508
        LD      R4, #%F5
        LD      R5, #%FF
        CP      %0B, #%FA
        JR      NZ, M_151A
        AND     %0D, #%7F
        LDE     @RR4, R13
M_151A: LD      R5, #%7F
        LDE     @RR4, R11
        LD      R6, #0
        RCF
M_1521: LDE     R7, @RR4
        ADC     R6, R7
        INC     R5
        JR      NZ, M_1521
        ADC     %6, #0
        LD      R5, #%7B
        INC     %5E
        LD      R7, %5E
        LDE     @RR4, R7
        INC     R5
        LDE     @RR4, R7
        INC     R5
        LDE     @RR4, R6
        INC     R5
        LDE     @RR4, R6
        LD      R5, #%7B
        LD      TMR, #3
        LD      R3, #%40
        CALL    M_14AA
        LD      R10, #%40
        LD      R3, #0
        LD      R6, #4
M_154C: CALL    M_14AA
        DJNZ    R6, M_154C
M_1551: LDE     R6, @RR4
        LD      R7, #8
M_1555: RL      %6
        XOR     R3, R10
        CALL    M_14AA
        JR      NC, M_1561
        CALL    M_14AA
M_1561: XOR     R3, R10
        CALL    M_14AA
        JR      NC, M_156B
        CALL    M_14AA
M_156B: DJNZ    R7, M_1555
        INC     R5
        JR      NZ, M_1551
        XOR     R3, R10
        CALL    M_14AA
        LD      TMR, #%43
        POP     RP
        RET

        NOP
        NOP
        NOP
        NOP
        NOP

M_1580: PUSH    RP
        SRP     #0
M_1584: LD      R4, #%F5
        LD      R5, #%7B
        LD      R10, #%3D
        LD      R9, #%2C
        LD      R6, %3
M_158E: CP      R3, R6
        JR      Z, M_158E
M_1592: LD      R6, %3
        LD      R7, #%58
M_1596: DEC     %7
        JR      Z, M_1592
        CP      R3, R6
        JR      Z, M_1596
        CP      R7, R9
        JR      NC, M_1592
M_15A2: LD      R8, #8
M_15A4: LD      R7, #0
        LD      R6, %3
M_15A8: INC     R7
        CP      R3, R6
        JR      Z, M_15A8
        LD      R6, %3
M_15AF: INC     R7
        CP      R3, R6
        JR      Z, M_15AF
        CP      R10, R7
        RLC     %9
        DJNZ    R8, M_15A4
        LDE     @RR4, R9
        INC     R5
        JR      NZ, M_15A2
        LD      R5, #%7D
        LD      R7, #0
        LDE     R10, @RR4
        INC     R5
        LDE     R9, @RR4
        INC     R5
        RCF
M_15CA: LDE     R6, @RR4
        ADC     R7, R6
        INC     R5
        JR      NZ, M_15CA
        ADC     %7, #0
        CP      R7, R9
        JR      NZ, M_161B
        CP      R7, R10
        JR      NZ, M_161B
        LD      R5, #%7B
        LDE     R9, @RR4
        INC     R5
        LDE     R10, @RR4
        LD      R6, %5E
        INC     R6
        CP      R6, R9
        JR      NZ, M_161B
        CP      R6, R10
        JR      NZ, M_161B
        LD      R5, #%7F
        LDE     R7, @RR4
        LD      R11, #0
        CP      %7, #%FC
        JR      Z, M_1613
        LD      R5, #%FF
        LDE     R8, @RR4
        LD      R11, #%80
        OR      R11, R8
        CP      %7, #%FA
        JR      Z, M_1613
        CP      %7, #%FE
        JR      NZ, M_161B
        LD      R14, #%88
M_160D: SCF
M_160E: LD      R12, #%F5
        POP     RP
        RET
M_1613: LD      %5E, R6
        LD      TMR, #3
        RCF
        JR      M_160E
M_161B: LD      TMR, #%43
M_161E: CALL    M_081B
        CP      %6D, #%42
        JR      Z, M_1637
        CP      %6D, #%43
        JR      NZ, M_161E
        LD      R4, #7
M_162D: DJNZ    R5, M_162D
        DJNZ    R4, M_162D
        LD      TMR, #3
        JP      M_1584
M_1637: LD      R14, #%FF
        JR      M_160D
        NOP
        NOP
        NOP
        NOP
        NOP
        CALL    M_0CA9
        LD      R8, R10
        LD      R9, R11
        CALL    M_0CA9
        CALL    M_0CDC
        JR      C, M_1657
M_164F: LDE     @RR8, R13
        INCW    R8
        DECW    R10
        JR      NZ, M_164F
M_1657: RET
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
M_1660: LD      R0, %51
        LD      R1, %4F
        LD      R3, #7
        AND     R3, R1
        RL      R1
        SWAP    R1
        AND     R1, #%0F
        SWAP    R0
        LD      R2, #%F0
        AND     R2, R0
        OR      R1, R2
        OR      R0, #%F8
        INC     R3
        LD      R2, #0
        SCF
M_167E: RRC     R2
        DJNZ    R3, M_167E
        RET
        NOP
M_1684: PUSH    RP
        SRP     #%60
        CALL    M_1660
        LDE     R3, @RR0
        TM      %53, #1
        JR      Z, M_1696
        OR      R3, R2
        JR      M_169A
M_1696: COM     R2
        AND     R3, R2
M_169A: LDE     @RR0, R3
        POP     RP
        RET
        NOP
M_16A0: PUSH    RP
        SRP     #%60
        CALL    M_1660
        LD      %53, #0
        LDE     R3, @RR0
        TM      R3, R2
        JR      Z, M_16B3
        LD      %53, #1
M_16B3: POP     RP
        RET
        NOP
        NOP
        LD      %51, R7
        LD      %4F, R6
        JP      M_1684
        NOP
        LD      %51, R6
        LD      %4F, R7
        JP      M_1684
        NOP
M_16C8: PUSH    RP
        SRP     #%70
        LD      R4, %4F
        LD      R5, %51
        LD      R6, %4B
        LD      R7, %4D
        LD      R8, #0
        LD      %72, #%16
        LD      %73, #%B8
        LD      R11, R6
        SUB     R11, R4
        JR      PL, M_16E5
        COM     R11
        INC     R11
M_16E5: LD      R12, R7
        SUB     R12, R5
        JR      PL, M_16EE
        COM     R12
        INC     R12
M_16EE: CP      R11, R12
        JR      NC, M_1704
        LD      %72, #%16
        LD      %73, #%C0
        LD      R11, R4
        LD      R4, R5
        LD      R5, R11
        LD      R11, R6
        LD      R6, R7
        LD      R7, R11
M_1704: CP      R6, R4
        JR      C, M_1714
        LD      R11, R4
        LD      R4, R6
        LD      R6, R11
        LD      R11, R5
        LD      R5, R7
        LD      R7, R11
M_1714: LD      R15, #0
        LD      R9, #0
        LD      R10, #0
        LD      R11, R5
        SUB     R11, R7
        JR      Z, M_174A
        JR      PL, M_1727
        LD      R15, #%80
        COM     R11
        INC     R11
M_1727: LD      R14, R4
        SUB     R14, R6
        LD      R12, #0
        CP      R11, R14
        JR      NZ, M_1734
        INC     R9
        JR      M_173E
M_1734: SUB     R12, R14
        SBC     R11, #0
        JR      MI, M_173E
        INC     R10
        JR      M_1734
M_173E: OR      R15, R15
        JR      PL, M_174A
        COM     R10
        COM     R9
        INC     R10
        JR      NZ, M_174A
        INC     R9
M_174A: CALL    @%72
        CP      R6, R4
        JR      NC, M_1757
        INC     R6
        ADD     R8, R10
        ADC     R7, R9
        JR      M_174A
M_1757: POP     RP
        RET

        NOP
        NOP
        NOP
        NOP
        NOP
        NOP

        LD      %2, #%30
        LD      P2M, #%0F
        NOP
        NOP

        PUSH    RP
        SRP     #%60
        LD      R0, #8
        LD      R1, %15
M_1770: LD      R2, #0
        RRC     %61
        RRC     %62
        OR      %62, #%30
        LD      R4, %62
        LD      %2, R2
        LD      R3, #%18
M_177F: DJNZ    R3, M_177F
        OR      %62, #%40
        LD      %2, R2
        LD      R3, #%18
M_1788: DJNZ    R3, M_1788
        LD      %2, R4
        LD      R3, #%18
M_178E: DJNZ    R3, M_178E
        DJNZ    R0, M_1770
M_1792: LD      R0, #8
        AND     %60, %3
        JR      NZ, M_1792
        LD      %2, #%10
        LD      R3, #%18
M_179E: DJNZ    R3, M_179E
        LD      %2, R4
        POP     RP
        RET

M_17A5: PUSH    RP
        SRP     #%50
        OR      R3, R3
        JR      NZ, M_17BC
        LD      R1, #%F0
        AND     R1, R11
        SWAP    %51
        LD      %4F, #%0F
        AND     %4F, %5B
M_17B9: POP     RP
        RET

M_17BC: DJNZ    R3, M_17CC
        LD      R11, #%0F
        AND     %5B, %4F
        AND     %51, #%0F
        SWAP    %51
        OR      R11, R1
        JR      M_17B9

M_17CC: LD      R14, #%F4
        LD      R15, #%0F
        AND     %5F, %4F
        AND     %51, #%0F
        SWAP    %51
        OR      R15, R1
        LDE     R3, @RR14
        JR      M_17B9
M_17DE: PUSH    %13
        CALL    M_081E
        LD      %5A, %13
        POP     %13
        RET

        .repeat %0b
            NOP
        .end

M_17F4: JP      M_17DE
M_17F7: JP      M_16C8
M_17FA: JP      M_16A0
M_17FD: JP      M_1684
