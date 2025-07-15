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

M_0800: JP      @%74
        NOP

M_0803: JP      @%76
        NOP

M_0806: JP      @%78
        NOP

M_0809: JP      @%7A
        NOP

M_080C: JP      @%7C    ; usually M_0ADA
        NOP

M_080F: JP      @%7E
        NOP

        ; boot
M_0812: JP      M_2000

M_0815: JP      M_081D

M_0818: LD      %5A, %15
        JR      M_0827

M_081D: CALL    M_0824
        LD      %13, %5A
        RET

M_0824: CALL    M_0C1D
M_0827: CP      %5A, #%7F
        JR      Z, .2
        CP      %5A, #%20
        JR      NC, M_0872
        CP      %5A, #%1B
        JR      Z, M_0875
        LD      %5C, #%F0
        CP      %5A, #%1A
        JR      Z, M_0878
        LD      %5C, #%10
        CP      %5A, #%0A
        JR      Z, M_0878
        LD      %5C, #%FF
        CP      %5A, #%0B
        JR      Z, M_0878
        CP      %5A, #8
        JR      NZ, .1
        CALL    M_0878
        LD      %5A, #%20
        CALL    M_0B95
        LD      %5A, #8
        RET

.1:     CP      %5A, #%0D
        JR      NZ, .3
        CALL    M_0AD4
        LD      %5A, #%0D
.2:     RET

.3:     CP      %5A, #%0C
        JR      Z, CLS
        RET

M_0872: CALL    M_0B95
M_0875: LD      %5C, #1
M_0878: ADD     %5B, %5C
        JR      OV, .2
        JR      MI, M_08FC
        LD      %60, %5C
        LD      %5C, %5B
        AND     %5C, #%0F
        CP      %5C, #%0D
        JR      C, M_08DC
        RL      %60
        JR      NC, .1
        SUB     %5B, #6
.1:     ADD     %5B, #3
        JR      NOV, M_08DC
.2:     PUSH    RP
        SRP     #%60
        LD      R0, #%FD
        LD      R1, #0
        LD      R2, #%FD
        LD      R3, #%10
        LD      R4, #%70
.3:     LDE     R5, @RR2
        LDE     @RR0, R5
        INC     R1
        INC     R3
        DJNZ    R4, .3
        LD      R4, #%10
        LD      R5, #%20
.4:     LDE     @RR0, R5
        INC     R1
        DJNZ    R4, .4
        LD      R1, #%38
        INC     R2
        INC     R0
        LD      R3, #%70
        LD      %5B, R3
.5:     LDE     R5, @RR2
        LDE     @RR0, R5
        INCW    R0
        INCW    R2
        CP      R3, #%F0
        JR      C, .5
        LD      R5, #%FF
        CP      R2, R5
        JR      C, .5
        LD      R4, #%38
.6:     LDE     @RR0, R5
        INC     R1
        DJNZ    R4, .6
        POP     RP
M_08DC: RET


M_08DD:
CLS:    PUSH    RP
        SRP     #%60
        LD      R4, #%FD
        LD      R5, #%80
        LD      R3, #%20
        LD      R2, #%80
.1:     DEC     R5
        LDE     @RR4, R3
        DJNZ    R2, .1
        LD      R2, #2
        LD      R3, #%FF
.2:     INC     R4
.3:     LDE     @RR4, R3
        DJNZ    R5, .3
        DJNZ    R2, .2
        POP     RP
M_08FC: CLR     %5B
        RET

        ; Basic
M_08FF: SRP     #%F0
        LD      R15, #0
        LD      R14, #%FE
        LD      R11, #%10
        LD      R9, #8
        LD      R8, #%92
        LD      R5, #7
        LD      R4, #%40
        LD      R1, #3
        LD      %6, #%E0
        CLR     %7
        CLR     %8
        CLR     P3M
        CALL    CLS          ; CLS
        EI
        LD      %58, #%14
        CALL    M_0BF2
M_0924: SRP     #%10
        LD      %5A, #'K'
        CALL    M_0B95
        CALL    M_0824
        LD      R6, %5A
        LD      R15, #%16
        CALL    %03D9        ; isUpperCaseLetter
        JR      NC, .1       ; no -> .1
        SUB     R6, #%31     ; 'A' -> %20, 'B' -> %22, ...
        ADD     R6, R6
        LD      R2, @R6
        INC     R6
        LD      R3, @R6
        LD      %5A, #'='
        CALL    M_0872
        CALL    M_0E92
        JR      M_0924

.1:     CP      R6, #'*'     ; continue
        JR      NZ, .2
        TM      %0F, #8
        JR      Z, .5
        AND     %0F, #%F7
        JR      .4

.2:     LD      %0F, #4
        CLR     %0E
        LD      R0, %6
        LD      R1, %7
        CP      R6, #8      ; Backspace (init)
        JR      NZ, .3
        LD      R4, #0
        LDE     @RR0, R4
        JR      M_0924

.3:     CP      R6, #'+'    ; run
        JR      NZ, .8
        CALL    %06C9
.4:     CALL    %0738
        CALL    M_0AD4
        LD      %58, #%0C
        TM      %0F, #2
        JR      NZ, .7
        DEC     %58
        TM      %0F, #8
        JR      NZ, .7
.5:     CALL    M_0E80
.6:     JR      M_0924

.7:     CALL    M_0BF2
        PUSH    R0
        PUSH    R1
        DECW    R0
        CALL    %0500
        LD      R2, R4
        LD      R3, R5
        CALL    M_0E92
        POP     R1
        POP     R0
        JR      .6

.8:     CP      R6, #'-'        ; list
        LD      %6E, #%0C
        JR      NZ, .10
        LD      %6F, #%E6
.9:     LDE     R2, @RR0
        OR      R2, R2
        JR      Z, .6
        INCW    R0
        LDE     R3, @RR0
        AND     R2, #%7F
        CALL    M_0AA3
        CALL    M_0DCC
        JR      NC, .5
        CLR     %6D
        CALL    M_0C1D
        CP      %5A, #'-'
        JR      NZ, .6
        INCW    R0
        JR      .9

.10:    CALL    %03E7       ; isDigit
        JR      NC, .17     ; no -> .17
        LD      R3, R6
        CALL    %02F4
        LD      R15, #%16
        LD      R8, #%80
        OR      R8, R4
        LD      R9, R5
        OR      R9, R5
        JR      NZ, .11
        INC     R9
.11:    LDE     R2, @RR0
        OR      R2, R2
        JR      Z, .13
        AND     R2, #%7F
        INCW    R0
        LDE     R3, @RR0
        CALL    %0141
        TM      %0F, #%40
        JR      NZ, .16
        TM      %0F, #%20
        JR      NZ, .12
        CALL    %0593
        INCW    R0
        JR      .11

.12:    DECW    R0
.13:    LD      %6F, #%D7
        LD      R6, R8
        CALL    M_0CA4
        LD      R6, R9
        CALL    M_0DCC
        JR      C, .14
        CALL    M_0A81
        INC     R6
        JR      MI, .15
        CALL    M_0E80
        JR      .15

.14:    CALL    M_0CA4
.15:    JP      M_0924

.16:    CALL    M_0A81
        JR      .13

.17:    CP      R6, #'_'        ; Shift+O (load)
        JR      NZ, .18
        DI
        CALL    M_27DF
        EI
        JR      .15

.18:    CP      R6, #'@'        ; Shift+P (save)
        JR      NZ, .15
        LD      %50, R1
        LD      %51, R0
.19:    LDE     R6, @RR0
        INCW    R0
        OR      R6, R6
        JR      NZ, .19
        LD      %52, R1
        LD      %53, R0
        CALL    CLS
        CALL    M_26AD          ; SAVE
        EI
        JR      .15

M_0A62: INCW    SPH
        INCW    SPH
        INC     %5D
        JR      NZ, .2
        LD      R0, #%FC
        LD      R1, #0
        LD      R2, #%0C
        LD      R3, #%0D
.1:     LD      %5A, R3
        CALL    M_0827
        LDE     R3, @RR0
        INCW    R0
        DJNZ    R2, .1
.2:     EI
        JP      M_0824

M_0A81: CALL    %0500
        LD      R2, R0
        LD      R3, R1
        LD      R4, R0
        LD      R5, R1
        INCW    R4
.1:     INCW    R4
        LDE     R7, @RR4
        RL      R7
        JR      UGT, .1
.2:     LDE     R7, @RR4
        LDE     @RR2, R7
        INCW    R2
        INCW    R4
        OR      R7, R7
        JR      NZ, .2
        RET

M_0AA3: CALL    %0182
        CP      R4, #%20
        JR      Z, M_0AAE
        CALL    M_0ABD
M_0AAE: LD      R11, #%15
M_0AB0: LD      R4, @R11
        CP      R4, #%30
        JR      NZ, M_0AC2
        INC     R11
        CP      R11, #%1A
        JR      C, M_0AB0
M_0ABD: LD      %5A, R4
        JP      M_0872

M_0AC2: CALL    M_0ABD
        INC     R11
        LD      R4, @R11
        CP      R11, #%1A
        JR      C, M_0AC2
        RET

M_0ACE: LD      %5A, #%20
        CALL    M_0872
M_0AD4: TM      %5B, #%0F
        JR      NZ, M_0ACE
        RET

M_0ADA: PUSH    RP
        SRP     #%50
        DJNZ    R4, M_0B3E
        LD      R6, SPH
        LD      R7, SPL
        LD      SPH, #%FE
        CLR     SPL
        LD      R4, #%40
M_0AEB: SWAP    %80
        OR      %3, #%80
        AND     %3, #%7F
        TM      %6D, #%80
        JR      Z, M_0AFD
        XOR     %3, #%40
        JR      M_0B02

M_0AFD: AND     %3, #%3F
M_0B00: JR      F, M_0B00
M_0B02: JR      F, M_0B02
        NOP
        POP     %80
        NOP
        POP     %80
        NOP
        POP     %80
        NOP
        POP     %80
        NOP
        POP     %80
        NOP
        POP     %80
        NOP
        POP     %80
        NOP
        POP     %80
        INC     R5
        JR      Z, M_0B27
        SUB     SPL, #8
        SBC     SPH, #0
        JR      M_0AEB

M_0B27: LD      R5, #%FD
        NOP
        NOP
        DJNZ    R4, M_0AEB
        AND     IRQ, #%EF
        OR      %3, #%80
        AND     %3, #%7F
        LD      SPH, R6
        LD      SPL, R7
        LD      R4, #%78
        JR      M_0B5A

M_0B3E: CP      R4, #%20
        JR      C, M_0B50
        CP      R4, #%25
        JR      NC, M_0B54
        AND     %3, #%7F
        OR      %3, #%80
        JR      M_0B5A

M_0B50: INCW    %56
        DECW    %56
M_0B54: OR      %3, #%80
        AND     %3, #%7F
M_0B5A: POP     RP
        IRET

M_0B5D: LD      R1, R2
        ADD     R1, R2
        ADD     R1, R2
        ADD     R1, #%E0
        LD      R0, #%0F
        RET

M_0B69: LD      R5, %5B
        AND     R5, #%0F
        LD      R3, R5
        RL      R5
        RL      R5
        ADD     R3, R5
        LD      R5, R3
        AND     R3, #7
        AND     R5, #%38
        RL      R5
        SWAP    R5
        LD      R6, %5B
        SWAP    R6
        LD      R4, #%FE
        AND     R6, #7
        INC     R6
M_0B8C: ADD     R5, #%38
        ADC     R4, #0
        DJNZ    R6, M_0B8C
        RET

M_0B95: PUSH    RP
        SRP     #%60
        LD      R0, #%FD
        LD      R1, %5B
        LD      R2, %5A
        LDE     @RR0, R2
        CALL    M_0B5D
        LD      R2, #3
        CALL    M_0B69
M_0BA9: LDC     R6, @RR0
        CALL    M_0BBB
        LDC     R6, @RR0
        SWAP    R6
        CALL    M_0BBB
        INC     R1
        DJNZ    R2, M_0BA9
        POP     RP
        RET

M_0BBB: LDE     R7, @RR4
        INCW    R4
        LDE     R8, @RR4
        OR      R3, R3
        JR      Z, M_0BCF
        LD      R10, R3
M_0BC7: RL      R8
        RLC     R7
        RLC     R9
        DJNZ    R10, M_0BC7
M_0BCF: AND     R7, #%0F
        AND     R6, #%F0
        OR      R7, R6
        OR      R3, R3
        JR      Z, M_0BE5
        LD      R10, R3
M_0BDD: RR      R9
        RRC     R7
        RRC     R8
        DJNZ    R10, M_0BDD
M_0BE5: LDE     @RR4, R8
        DECW    R4
        LDE     @RR4, R7
        ADD     R5, #8
        ADC     R4, #0
        RET

M_0BF2: PUSH    RP
        SRP     #%50
        LD      R14, #%0E
        LD      R15, #%98
M_0BFA: LD      R9, #%5A
        LDCI    @R9, @RR14
        TM      R10, #%80
        JR      Z, M_0BFA
        DEC     R8
        JR      NZ, M_0BFA
        AND     R10, #%7F
M_0C0A: CALL    M_0827
        LD      R9, #%5A
        LDCI    @R9, @RR14
        TM      R10, #%80
        JR      Z, M_0C0A
        LD      R10, #%20
        POP     RP
        JP      M_0872

M_0C1D: CP      %54, #%75
        JR      NZ, M_0C1D
        PUSH    RP
        SRP     #%60
        LD      R2, #5
        CALL    M_0B69
M_0C2B: LD      R6, #0
        CALL    M_0BBB
        DJNZ    R2, M_0C2B
        LD      R0, #%FD
        LD      R1, %5B
        LDE     R2, @RR0
        LD      %5A, R2
        POP     RP
M_0C3C: CP      %54, #%75
        JR      NZ, M_0C3C
        CALL    M_0B95
        CALL    M_0C56
        TM      %6D, #%7F
        JR      Z, M_0C1D
        LD      %5A, %6D
        AND     %6D, #%80
        AND     %5A, #%7F
        RET

M_0C56: PUSH    RP
        SRP     #%60
        LD      R0, #%7F
        LD      R1, #%0F
M_0C5E: LDC     R2, @RR0
        AND     R2, #%F0
        JR      NZ, M_0C6C
        DJNZ    R1, M_0C5E
        AND     R13, R0
M_0C69: POP     RP
        RET

M_0C6C: OR      R13, R13
        JR      NZ, M_0C69
        LD      R13, #%80
        LD      R3, #%40
M_0C74: SUB     R3, #%10
        RL      R2
        JR      NC, M_0C74
        OR      R1, R3
        LD      R0, #%0F
        LDC     R3, @RR0
        LD      R0, #%FF
        LD      R1, #%FF
        LDE     R2, @RR0
        CP      R3, #%15
        JR      Z, M_0C9E
        OR      R2, R2
        JR      NZ, M_0C9A
        XOR     R3, #%10
        CP      R3, #%1D
        JR      NZ, M_0C9A
        LD      R3, #%7F
M_0C9A: OR      R13, R3
        LD      R2, #0
M_0C9E: COM     R2
        LDE     @RR0, R2
        JR      M_0C69
M_0CA4: LD      R2, R0
        LD      R3, R1
M_0CA8: LDE     R7, @RR2
        INCW    R2
        OR      R7, R7
        JR      NZ, M_0CA8
M_0CB0: LDE     @RR2, R7
        DECW    R2
        CP      R3, R1
        JR      Z, M_0CC0
M_0CB8: DECW    R2
        LDE     R7, @RR2
        INCW    R2
        JR      M_0CB0

M_0CC0: CP      R0, R2
        JR      C, M_0CB8
        LDE     @RR0, R6
        INCW    R0
        RET

M_0CC9: CALL    M_0CA4
        JR      Z, M_0CD6
M_0CCE: CALL    M_0824
        LD      R6, %5A
M_0CD3: CP      R6, #%60
M_0CD6: RET

M_0CD7: CALL    M_0CC9
        JR      Z, M_0CD6
M_0CDC: CP      R6, #%20
        JR      NZ, M_0CD3
        CALL    M_0CCE
        JR      M_0CDC

M_0CE6: INCW    R0
        LDE     R6, @RR0
        LD      %5A, R6
        CALL    M_0827
        OR      R6, R6
        JR      NZ, M_0CD3
        RET

M_0CF4: CALL    M_0D32
        JR      NC, M_0D6A
        CALL    M_0D09
        JR      NZ, M_0D56
        CALL    @%6E
        JR      NC, M_0D6A
        CALL    M_0D09
        JR      Z, M_0D32
        JR      M_0D36

M_0D09: CP      R6, #%3C
        JR      Z, M_0D6A
        CP      R6, #%3D
        JR      Z, M_0D6A
        CP      R6, #%3E
        RET

M_0D17: CP      %6F, #%E6
        JR      Z, M_0D1F
        LD      %6F, #%C9
M_0D1F: CALL    @%6E
        JR      NC, M_0D6A
        CP      R6, #%22
        JR      NZ, M_0D1F
        CP      %6F, #%E6
        JR      Z, M_0D30
        LD      %6F, #%D7
M_0D30: JP      @%6E

M_0D32: CALL    @%6E
        JR      NC, M_0D6A
M_0D36: CALL    M_0D58
        JR      NC, M_0D6A
        LD      R10, #3
        LD      R11, #%C1
        LD      R13, #3
        CALL    %03AF
        JR      Z, M_0D32
        CP      R6, #%24
        JR      NZ, M_0D69
        CALL    @%6E
        JR      NC, M_0D6A
        LD      R13, #3
        CALL    %03AF
        JR      Z, M_0D32
M_0D56: RCF
        RET

M_0D58: CP      R6, #%28
        JR      NZ, M_0D6B
        CALL    M_0D32
        JR      NC, M_0D6A
        CP      R6, #%29
        JR      NZ, M_0D56
        JP      @%6E

M_0D69: SCF
M_0D6A: RET

M_0D6B: CALL    %03D9
        JR      NC, M_0D8E
        CALL    @%6E
        JR      NC, M_0D6A
        CALL    %03E2
        JR      NC, M_0D69
M_0D79: CALL    @%6E
        JR      NC, M_0D6A
        CP      R6, #%3B
        JR      Z, M_0D69
        CP      R6, #%0D
        JR      Z, M_0D69
        CP      R6, #%5D
        JR      NZ, M_0D79
        JP      @%6E

M_0D8E: LD      R4, #5
        CALL    %03E7
        JR      C, M_0DAD
        CP      R6, #%2D
        JR      Z, M_0DAC
        CP      R6, #%25
        JR      NZ, M_0D56
M_0D9F: CALL    @%6E
        JR      NC, M_0D6A
        CALL    %03F2
        JR      NC, M_0DB8
        DJNZ    R4, M_0D9F
        RCF
        RET

M_0DAC: INC     R4
M_0DAD: CALL    @%6E
        JR      NC, M_0D6A
        CALL    %03E7
        JR      NC, M_0DB8
        DJNZ    R4, M_0DAD
M_0DB8: CCF
        RET

M_0DBA: .data   %02 %27 %0d %37 %23 %23 %0D %0D %27 %0D %23 %23 %3A %23 %60 %60 %50 %FF

M_0DCC: LD      %5A, #%41
        CALL    M_0B95
        CLR     %58
        CALL    @%6E
        JR      NC, M_0DEB
        LD      R2, #7
        LD      R3, #%C8
M_0DDC: LD      R13, #%17
        LDCI    @R13, @RR2
        INC     %58
        CP      R6, R7
        JR      Z, M_0DEC
        CP      %58, #%12
        JR      C, M_0DDC
M_0DEB: RET

M_0DEC: LD      R3, %58
        ADD     R3, #%B9
        LD      %5C, #%FF
        CALL    M_0878
        CALL    M_0BF2
        LD      R2, #%0D
        LD      R4, #%0E
        LDC     R5, @RR2
        JP      @RR4

M_0E02: CALL    @%6E
        CALL    %03D9
        JR      NC, M_0DEB
        CALL    @%6E
        LD      R6, #%3D
M_0E0D: CALL    M_0D32
        JR      NC, M_0DEB
        CP      R6, #%2C
        JR      Z, M_0E02
M_0E17: CP      R6, #%3B
        JR      Z, M_0DCC
        CP      R6, #%0D
        JR      Z, M_0E35
M_0E21: RCF
        RET

M_0E23: CALL    @%6E
        JR      M_0E17

M_0E27: CALL    @%6E
        JR      NC, M_0DEB
        CP      R6, #%3B
        JR      Z, M_0DCC
        CP      R6, #%0D
        JR      NZ, M_0E27
M_0E35: SCF
        RET

M_0E37: LD      R5, #0
        JP      F, %5C01        ; M_0E3A: LD R5, #1
        CALL    M_0CF4
        JR      NC, M_0DEB
        OR      R5, R5
        JR      NZ, M_0E0D
        LD      R6, #%3B
        LD      %58, #%13
        CALL    M_0BF2
M_0E4D: JP      M_0DCC

M_0E50: CALL    @%6E
        CP      R6, #%22
        JR      NZ, M_0E5A
        CALL    M_0D17
M_0E5A: CALL    %03D9
        JR      C, M_0E23
        RET

M_0E60: CALL    @%6E
        CP      R6, #%22
        JR      NZ, M_0E6A
        CALL    M_0D17
M_0E6A: CP      R6, #%3B
        JR      Z, M_0E4D
        CP      R6, #%0D
        JR      Z, M_0E35
        CALL    M_0D36
        JR      NC, M_0E21
        CP      R6, #%2C
        JR      NZ, M_0E17
        JR      M_0E60

M_0E80: CALL    M_0AD4
        LD      %58, #%12
        CALL    M_0BF2
        CLR     R2
        LD      R3, %0E
        AND     R3, #%F0
        SWAP    R3
M_0E92: CALL    M_0AA3
        JP      M_0AD4

        ; read from M_0BF2
M_0E98: .data   %CC "ET"
        .data   %D0 "ROC"
        .data   %C7 "OTO"
        .data   %C9 "F"
        .data   %C5 "LSE"
        .data   %D2 "ETURN"
        .data   %C7 "OSUB"
        .data   %D7 "AIT"
        .data   %D2 "EM"
        .data   %C3 "ALL"
        .data   %D3 "TOP"
        .data   %C5 "ND"
        .data   %D4 "RAP"
        .data   %D4 "OFF"
        .data   %D0 "RINT"
        .data   %D0 "TH"
        .data   %C9 "NPUT"
        .data   %C5 "RROR"
        .data   %8B "THEN"
        .data   %C3 "OMP JU+TE R \\\\\\\\\\\\\\\\\\\\\\\\\n"
        .data   %FF

M_0F00: .data   %00 " YXCVBNM,./\r" %1B %00 %00
        .data   %00 %15 "ASDFGHJKL*-\n" %00 %00
        .data   %00 "=QWERTZUIOP+" %0B %00 %00
        .data   %00 %0A "1234567890" %08 %1A %00 %00

M_0FA9: .data   %FF %FF %FF %DD %DF %DF %AA %FF %FF %50 %50 %5F %69 %69 %6F %6D
        .data   %B7 %6F %6D %B7 %AF %DD %FF %FF %DB %BB %DF %BD %DD %BF %69 %09
        .data   %6F %FD %8D %FF %FF %FD %BF %FF %0F %FF %FF %FF %BF %ED %B7 %7F
        .data   %94 %62 %9F %D9 %DD %8F %1E %97 %0F %1E %9E %1F %75 %0D %DF %07
        .data   %1E %1F %97 %16 %9F %0E %DB %BF %96 %96 %9F %96 %8E %9F %FB %FB
        .data   %FF %FD %FD %BF %DB %7B %DF %F0 %F0 %FF %BD %ED %BF %96 %DF %DF
        .data   %86 %47 %8F %96 %60 %6F %16 %16 %1F %87 %77 %8F %16 %66 %1F %07
        .data   %17 %0F %07 %17 %7F %87 %46 %8F %66 %06 %6F %8D %DD %8F %0E %E6
        .data   %9F %65 %35 %6F %77 %77 %0F %60 %06 %6F %62 %46 %6F %96 %66 %9F
        .data   %16 %17 %7F %96 %64 %8F %16 %15 %6F %87 %9E %1F %8D %DD %DF %66
        .data   %66 %9F %AA %AA %DF %66 %00 %9F %66 %96 %6F %AA %DD %DF %0D %B7
        .data   %0F %9B %BB %9F %F0 %66 %0F %9D %DD %9F %7B %DE %EF %FF %FF %0F

        .repeat %1000
            .data %ff
        .end

        ; boot
M_2000: DI
        SRP     #%F0
        LD      R15, #0         ; SPL = 0
        LD      R14, #%FE       ; SPH = 0xFE
        LD      R11, #%10       ; IMR
        LD      R9, #8          ; IPR
        LD      R8, #%92        ; P01M
M_200D: LD      R5, #7          ; PRE0
        LD      R4, #%40        ; T0
        LD      R1, #3          ; TMR
        CLR     %6D
        SRP     #%60
        LD      R12, #4
        LD      %7C, #hi(M_0ADA)
        LD      %7D, #lo(M_0ADA)
M_201F: LD      %58, R12
        CALL    M_210A
        DJNZ    R12, M_201F
        EI
        CALL    M_0824
        CP      %5A, #%42   ; B ... Basic
        JP      Z, M_08FF
        CP      %5A, #%53   ; S ... Save
        JR      NZ, M_2038
        CALL    M_268F
M_2038: CP      %5A, #%4C   ; L ... Load
        JR      NZ, M_2040
        CALL    M_27D1
M_2040: CP      %5A, #%52   ; R ... Data
        JR      NZ, M_2048
        CALL    M_2352
M_2048: CP      %5A, #%50   ; P ... Prog
        JP      Z, M_23E4
        CP      %5A, #%49   ; I ... Init
        JR      NZ, M_2000
        CALL    M_0824
        CP      %5A, #%21   ; !
        JR      NZ, M_2000
        DI
        LD      R0, #%FD
        LD      R1, #0
        LD      R2, #0
        LD      R3, #8
M_2064: DECW    R0
        LDE     @RR0, R2
        CP      R3, R0
        JR      C, M_2064
        JR      M_2000

M_206E: .data   %D0 "ROG"
    	.data   %8D "  P"
    	.data   %C4 "ATA"
    	.data   %8C " EMR-ES 1988\r \r  B BASIC\r  I INIT\r  S SAVE\r  L LOAD\r  R"
    	.data   %8C "MTB"

M_20B6: CALL    M_20B9
M_20B9: SWAP    %5D
        LD      %5A, %5D
M_20BE: AND     %5A, #%0F
        CP      %5A, #%0A
        JR      C, M_20C9
        ADD     %5A, #7
M_20C9: ADD     %5A, #%30
        JP      M_0872

M_20CF: PUSH    RP
        SRP     #%50
        LD      R12, #%5D
        LDEI    @R12, @RR14
        POP     RP
        JR      M_20B6

M_20DB: LD      %5D, %5E
        CALL    M_20B6
        LD      %5D, %5F
        JR      M_20B6

M_20E6: LD      %6B, #3
        CP      %5D, #%D4
        JR      Z, M_2103
        LD      %6A, %5D
        AND     %6A, #%0F
        CP      %6A, #%0E
        JR      NC, M_2106
        CP      %6A, #%0C
        JR      Z, M_2103
        TM      %6A, #4
        JR      NZ, M_2109
M_2103: DEC     %6B
        RET

M_2106: LD      %6B, #1
M_2109: RET

M_210A: PUSH    RP
        SRP     #%50
        LD      R14, #%20
        LD      R15, #%6E
        JP      M_0BFA

M_2115: SUB     R1, #4
        SBC     R0, #0
        LDE     R3, @RR0
        DECW    R0
        LDE     R2, @RR0
        ADD     R1, #5
        ADC     R0, #0
        RET

M_2128: CALL    M_2115
        SUB     R3, R5
        SBC     R2, R4
        RET

M_2130: LD      R0, %52
        LD      R1, %53
M_2134: LDE     R2, @RR0
        OR      R2, R2
        JR      Z, M_2149
        CALL    M_2128
        OR      R2, R3
        JR      Z, M_2149
M_2141: SUB     R1, #6
        SBC     R0, #0
        JR      M_2134
M_2149: RCF
        LDE     R2, @RR0
        OR      R2, R2
        JR      Z, M_2154
        CP      R2, #%3A
        SCF
M_2154: RET

M_2155: LD      R0, %52
        LD      R1, %53
M_2159: LD      R2, #%69
M_215B: LDE     R3, @RR0
        CP      R3, @R2
        JR      NZ, M_2171
        DECW    R0
        DEC     R2
        CP      R2, #%66
        JR      NC, M_215B
        ADD     R1, #4
        ADC     R0, #0
        RET

M_2171: OR      R3, R3
        JR      NZ, M_2177
M_2175: SCF
        RET

M_2177: SUB     R2, #%63
        SUB     R1, R2
        SBC     R0, #0
        JR      M_2159

M_2181: CALL    M_2130
M_2184: JR      NC, M_218D
        JR      NZ, M_218D
        CALL    M_2141
        JR      M_2184

M_218D: SUB     R1, #5
        SBC     R0, #0
        LD      R2, #6
        LD      R3, #%64
M_2197: LDEI    @RR0, @R3
        DJNZ    R2, M_2197
M_219B: RET

M_219C: CALL    M_2155
        JR      NC, M_2175
        CALL    M_2130
M_21A4: JR      Z, M_218D
        CALL    M_2141
        JR      M_21A4

M_21AB: LD      R10, %52
        LD      R11, %53
M_21AF: LD      R0, R10
        LD      R1, R11
        CALL    M_2149
        JR      NC, M_219B
        JR      Z, M_21F7
        LD      R2, #3
        LD      R3, #%66
        LD      R9, #%3A
        DECW    R0
        DECW    R0
M_21C4: LDEI    @R3, @RR0
        DJNZ    R2, M_21C4
        CALL    M_2155
        JR      C, M_219B
        CALL    M_2115
        LD      R0, R10
        LD      R1, R11
        LD      R4, R2
        LD      R5, R3
        CALL    M_2115
        LDE     R6, @RR2
        TM      R6, #4
        JR      Z, M_21EE
        ADD     R5, %71
        ADC     R4, %70
        INCW    R2
        LDE     @RR2, R4
        JR      M_21F3

M_21EE: SUB     R5, R3
        SUB     R5, #2
M_21F3: INCW    R2
        LDE     @RR2, R5
M_21F7: SUB     R11, #6
        SBC     R10, #0
        JR      M_21AF

M_21FF: LD      R6, R4
        LD      R7, R5
M_2203: LD      R10, #%FC
M_2205: INCW    R6
        LDE     R11, @RR6
        OR      R11, R11
        JR      NZ, M_2203
        INC     R10
        JR      NZ, M_2205
        RET

M_2211: CALL    M_21FF
        LD      R10, R6
        LD      R11, R7
        DECW    R10
        LD      R8, R6
        LD      R9, R7
        SUB     R9, R5
        SBC     R8, R4
M_2222: LDE     R12, @RR10
        LDE     @RR6, R12
        DECW    R6
        DECW    R10
        DECW    R8
        JR      NZ, M_2222
        LD      R10, #%FF
        LD      R11, #%FF
        LDE     @RR4, R10
M_2234: LD      R0, %52
        LD      R1, %53
M_2238: CALL    M_2128
        JR      C, M_2259
        SUB     R1, #4
        SBC     R0, #0
        LDE     R3, @RR0
        SUB     R3, R11
        LDE     @RR0, R3
        DECW    R0
        LDE     R2, @RR0
        SBC     R2, R10
        LDE     @RR0, R2
        DECW    R0
M_2253: CALL    M_2149
        JR      C, M_2238
        RET

M_2259: SUB     R1, #6
        SBC     R0, #0
        JR      M_2253

M_2261: CALL    M_21FF
        LDE     R12, @RR4
        LD      %5D, R12
        CALL    M_20E6
        PUSH    R11
        ADD     R11, R5
        CLR     R10
        ADC     R10, R4
        SUB     R7, R11
        SBC     R6, R10
        LD      R8, R4
        LD      R9, R5
M_227B: LDE     R12, @RR10
        LDE     @RR8, R12
        INCW    R10
        INCW    R8
        DECW    R6
        JR      NZ, M_227B
M_2287: CALL    M_2130
        JR      NC, M_22AE
        LD      R8, R0
        LD      R9, R1
        LD      R10, R0
        LD      R11, R1
        SUB     R11, #6
        SBC     R10, #0
M_229A: LD      R6, #6
M_229C: LDE     R12, @RR10
        LDE     @RR8, R12
        DECW    R8
        DECW    R10
        DJNZ    R6, M_229C
        LDE     R12, @RR8
        OR      R12, R12
        JR      NZ, M_229A
        JR      M_2287

M_22AE: LD      R10, #0
        POP     R11
        JR      M_2234

M_22B4: CALL    M_0824
        CP      %5A, #%30
        JR      C, M_22C1
        CP      %5A, #%46
        JR      ULE, M_22C3
M_22C1: SCF
        RET

M_22C3: CP      %5A, #%3A
        JR      Z, M_22C1
        RCF
        RET

M_22CA: SRP     #%50
        LD      R11, #%70
        LD      R14, %70
        LD      R15, %71
        CALL    M_20DB
        INC     R11
        SRP     #%60
        LD      R12, #6
        LD      %5A, #%20
M_22DD: CALL    M_0872
        DJNZ    R12, M_22DD
        SUB     %5B, #6
M_22E5: CALL    M_22B4
        JR      C, M_22F3
        LD      R12, %5A
        CALL    M_22B4
        JR      NC, M_22F4
        OR      R12, R12
M_22F3: RET

M_22F4: CALL    M_2306
        LD      R3, %5A
        LD      %5A, R12
        CALL    M_2306
        LD      R12, %5A
        SWAP    R12
        OR      R12, R3
        RCF
        RET

M_2306: CP      %5A, #%3A
        JR      C, M_230E
        ADD     %5A, #9
M_230E: AND     %5A, #%0F
        RET
M_2312: CP      %5A, #%2B
        JR      NZ, M_231A
        INCW    R14
M_2319: RET

M_231A: CP      %5A, #%47
        JR      NZ, M_2319
M_231F: CALL    M_22E5
        JR      NC, M_2348
        JR      NZ, M_2319
        LD      R12, #3
M_2328: CALL    M_0824
        PUSH    %5A
        DJNZ    R12, M_2328
        LD      R12, #3
        LD      R9, #%68
M_2333: POP     @R9
        DEC     R9
        DJNZ    R12, M_2333
        LD      R9, #%3A
        CALL    M_2155
        JR      C, M_2319
        CALL    M_2115
        LD      R14, R2
        LD      R15, R3
        RET

M_2348: LD      R14, R12
        CALL    M_22E5
        JR      C, M_2319
        LD      R15, R12
        RET

M_2352: CALL    CLS
        PUSH    RP
M_2357: SRP     #%60
        LD      %70, R14
        LD      %71, R15
M_235D: SRP     #%50
        LD      R8, #3
        CALL    M_210A
        LD      R14, %6E
        LD      R15, %6F
        CALL    M_20DB
        CALL    M_0ACE
        SRP     #%60
        LD      R12, #6
M_2372: LD      R11, #4
        LD      %5A, %5F
        CALL    M_20BE
M_237A: LD      %5A, #%20
        CALL    M_0872
        OR      R14, R14
        JR      Z, M_2389
        CALL    M_20CF
        JR      M_2391

M_2389: LD      %5D, @%5F
        INC     %5F
        CALL    M_20B6
M_2391: DJNZ    R11, M_237A
        DJNZ    R12, M_2372
        CALL    M_22CA
        JR      C, M_23AF
        SRP     #%70
        OR      R0, R0
        JR      Z, M_23A6
        LD      R2, #%6C
        LDEI    @RR0, @R2
        JR      M_23AB

M_23A6: LD      @R1, %6C
        INCW    R0
M_23AB: CLR     %5B
        JR      M_235D

M_23AF: CP      %5A, #%60
        JR      C, M_23B7
        POP     RP
        RET

M_23B7: CP      %5A, #%0D
        JR      NZ, M_23C5
        LD      R13, #0
        ADD     R15, #4
        ADC     R14, R13
        JR      M_23DF

M_23C5: CP      %5A, #%2D
        JR      NZ, M_23DC
        DECW    %70
        LD      R4, %70
        LD      R5, %71
        SUB     R5, R15
        SBC     R4, R14
        JR      NC, M_23AB
        LD      R14, %70
        LD      R15, %71
        JR      M_23AB

M_23DC: CALL    M_2312
M_23DF: CLR     %5B
        JP      M_2357

M_23E4: PUSH    FLAGS
        PUSH    RP
        SRP     #%50
        LD      R8, #5
        CALL    M_210A
        LD      R14, R2
        LD      R15, R3
        CALL    M_20DB
        LD      R11, #4
        SRP     #%60
        CALL    M_231F
        JR      C, M_2403
        LD      %52, R14
        LD      %53, R15
M_2403: POP     RP
M_2405: DI
        CALL    CLS
        LD      %58, #1
        CALL    M_210A
        POP     %6C
        PUSH    %6C
        PUSH    RP
        SRP     #%50
        LD      R15, #8
M_2419: LD      R10, #%30
        RL      %6C
        ADC     R10, R8
        CALL    M_0872
        DJNZ    R15, M_2419
        LD      R15, %6F
        LD      R14, %6E
        CLR     %70
        LDE     R13, @RR14
        CALL    M_20E6
        LD      %71, R15
        ADD     %71, %6B
        ADC     %70, R14
M_2437: CALL    M_260E
        CP      %5B, #%70
        JR      C, M_2437
        EI
M_2440: CALL    M_22CA
        JR      NC, M_2471
        JP      NZ, M_24D3
        LD      %5B, #%73
        CALL    M_0B95
        LD      %5B, #%70
        LD      R12, #3
M_2453: CALL    M_0824
        PUSH    %5A
        DJNZ    R12, M_2453
        LD      R12, #3
        LD      R9, #%68
M_245E: POP     @R9
        DEC     R9
        DJNZ    R12, M_245E
        LD      R9, #%3A
        LD      R4, %70
        LD      R5, %71
        CALL    M_219C
        JR      NC, M_2484
        JR      M_2440

M_2471: SRP     #%70
        LD      R3, %6C
        LD      %5D, R3
        LDE     @RR0, R3
        SRP     #%60
        CALL    M_20E6
        LD      R14, %70
        LD      R15, %71
        DJNZ    R11, M_2489
M_2484: POP     RP
        JP      M_2405

M_2489: CALL    M_22E5
        JR      NC, M_24C0
        JR      NZ, M_2484
        CP      %73, #%D6
        JR      Z, M_24A2
        AND     %73, #%0F
        CP      %73, #%0C
        JR      Z, M_2484
        CP      %73, #%0A
        JR      C, M_2484
M_24A2: LD      R12, #3
M_24A4: CALL    M_0824
        PUSH    %5A
        DJNZ    R12, M_24A4
        LD      R12, #3
        LD      R6, #%69
M_24AF: POP     @R6
        DEC     R6
        DJNZ    R12, M_24AF
        LD      R4, %70
        LD      R5, %71
        LD      R6, #%3A
        CALL    M_2181
        JR      M_2484

M_24C0: INCW    %70
        SRP     #%70
        LD      R3, %6C
        LDE     @RR0, R3
        DEC     %6B
        JR      Z, M_2484
        SRP     #%60
        CALL    M_22E5
        JR      M_24C0

M_24D3: CP      %5A, #%51
        JR      NZ, M_24DF
        LD      %70, R14
        LD      %71, R15
M_24DC: JP      M_2440

M_24DF: CP      %5A, #%2D
        JR      NZ, M_24EA
        DECW    %6E
        CLR     %6D
M_24E8: JR      M_2484

M_24EA: CP      %5A, #%54
        JR      NZ, M_2533
        PUSH    R14
        PUSH    R15
        CALL    M_231F
        CLR     %70
        CLR     %71
        POP     R5
        POP     R4
        JR      C, M_2508
        SUB     R15, R5
        SBC     R14, R4
        LD      %70, R14
        LD      %71, R15
M_2508: LD      R14, R4
        LD      R15, R5
        DI
        CALL    M_21AB
        JP      NC, M_2484
        EI
        LD      R9, #%3F
        PUSH    R9
        LD      R12, #3
        LD      R9, #%68
M_251C: PUSH    @R9
        DEC     R9
        DJNZ    R12, M_251C
        LD      %5B, #%75
        LD      R12, #4
M_2527: POP     %5A
        CALL    M_0872
        DJNZ    R12, M_2527
        CALL    M_0AD4
        JR      M_24DC

M_2533: CP      %5A, #%49
        LD      R4, %70
        LD      R5, %71
        JR      NZ, M_2541
        CALL    M_2211
        JR      M_24E8

M_2541: CP      %5A, #%58
        JR      NZ, M_254B
        CALL    M_2261
        JR      M_24E8

M_254B: LDE     R11, @RR14
        LD      %5D, R11
        CALL    M_20E6
        CP      %5A, #%0D
        JR      NZ, M_255F
        CLR     R13
        ADD     R15, R11
        ADC     R14, R13
        JR      M_24E8

M_255F: CP      %5A, #%4E
        JR      NZ, M_2570
        CLR     %70
        LD      %71, R11
        ADD     %71, R15
        ADC     %70, R14
        JR      M_2587

M_2570: CP      %5A, #%48
        JR      NZ, M_258B
        PUSH    %6E
        PUSH    %6F
        CALL    M_231F
        LD      %70, R14
        LD      %71, R15
        POP     %6F
        POP     %6E
        JP      C, M_24E8
M_2587: POP     RP
        JR      M_25C7

M_258B: POP     RP
        CP      %5A, #%4C
        JR      NZ, M_2596
        POP     FLAGS
        JP      @%6E

M_2596: CP      %5A, #%52
        JR      NZ, M_25AE
        PUSH    %6E
        PUSH    %6F
        CLR     %6E
        LD      %6F, RP
        CALL    M_2352
        POP     %6F
        POP     %6E
M_25AB: JP      M_2405

M_25AE: CP      %5A, #%60
        JR      C, M_25B6
        JP      M_2000

M_25B6: CP      %5A, #%53
        JR      Z, M_25C5
        PUSH    RP
        SRP     #%60
        CALL    M_2312
        JP      M_2484

M_25C5: CLR     %70
M_25C7: DI
        AND     TMR, #%FC
        LD      %7C, #%25
        LD      %7D, #%E3
        AND     IRQ, #%2F
        LD      T0, #4
M_25D7: POP     FLAGS
        PUSH    %6F
        PUSH    %6E
        PUSH    FLAGS
        OR      TMR, #3
        IRET

M_25E3: AND     TMR, #%FC
        POP     FLAGS
        POP     %6E
        POP     %6F
        PUSH    FLAGS
        AND     IRQ, #%2F
        OR      %70, %70
        JR      Z, M_2600
        CP      %70, %6E
        JR      NZ, M_25D7
        CP      %71, %6F
        JR      NZ, M_25D7
M_2600: LD      %7C, #%0A
        LD      %7D, #%DA
        LD      T0, #%40
        OR      TMR, #3
        JR      M_25AB

M_260E: SRP     #%60
        LD      R4, %5E
        LD      R5, %5F
        CALL    M_2130
M_2617: JR      NC, M_2635
        JR      Z, M_2620
        CALL    M_2141
        JR      M_2617

M_2620: LD      R12, #4
M_2622: LDE     R2, @RR0
        PUSH    R2
        DECW    R0
        DJNZ    R12, M_2622
        LD      R12, #4
M_262C: POP     %5A
        CALL    M_0872
        DJNZ    R12, M_262C
        JR      M_2638

M_2635: CALL    M_20DB
M_2638: SRP     #%50
        LD      R10, #%20
        CALL    M_0872
        LDE     R13, @RR14
        CALL    M_20B6
        CALL    M_20E6
        LD      R10, #%20
        CALL    M_0872
        SRP     #%60
        LD      R4, %5E
        LD      R5, %5F
        CALL    M_2130
M_2655: JR      NC, M_2679
        JR      NZ, M_265E
        CALL    M_2141
        JR      M_2655

M_265E: ADD     %5F, %6B
        ADC     %5E, #0
        LD      R12, #4
M_2666: LDE     R2, @RR0
        PUSH    R2
        DECW    R0
        DJNZ    R12, M_2666
        LD      R12, #4
M_2670: POP     %5A
        CALL    M_0872
        DJNZ    R12, M_2670
        JR      M_268C
M_2679: INCW    %5E
        DEC     R11
        JR      Z, M_268C
M_267F: CALL    M_20CF
        LD      %5A, #%20
        CALL    M_0872
        DJNZ    R11, M_267F
        DEC     %5B
M_268C: JP      M_0AD4

M_268F: CALL    CLS
        CALL    M_231F
        JR      C, M_268F
        LD      %50, R15
        LD      %51, R14
        INC     %5B
        CALL    M_231F
        JR      C, M_26A6
        LD      %52, R14
        LD      %53, R15
M_26A6: LD      R14, %52
        LD      %52, %53
        LD      %53, R14
        ; SAVE
        ; 50=Low(start)     yes, low and high are swapped!
        ; 51=High(start)
        ; 52=Low(size)
        ; 53=High(size)
M_26AD: LD      %5B, #%20
.1:     CALL    M_0824
        CP      %5A, #%0D
        JR      NZ, .1
        DI
        LD      R0, #%FC
        LD      %3, R0
        LD      R1, #0
        LD      R2, #%FD
        LD      R3, #%20
        ; repeat(11) {
        LD      R4, #%0B
.2:     LDE     R5, @RR2
        LDE     @RR0, R5
        INC     R1
        INC     R3
        DJNZ    R4, .2
        ; }
        ; { fill all bytes up until incl. %FCFF with %00
.3:     LDE     @RR0, R4
        INC     R1
        JR      PL, .3
        ; }
        LD      R5, #2
        LD      R1, #%10
        LDE     @RR0, R5    ; %FC10 = %02
        INC     R1
        ; { %FC11 = Low (start)
        ;   %FC12 = High (start)
        ;   %FC13 = Low (size)
        ;   %FC14 = High (size)
        LD      R5, #%50
        LD      R4, #4
.4:     LDEI    @RR0, @R5
        DJNZ    R4, .4
        ; }
        LD      R1, #0
        LD      R6, #1
        LD      R12, #%20
        CALL    M_274E
        LD      R0, %51
        LD      R1, %50
        LD      R2, %53
        LD      R3, %52
.5:     INC     R6
        LD      R4, R2
        LD      R5, R3
        SUB     R5, R1
        SBC     R4, R0
        JR      NC, .6
        RET

.6:     SUB     R5, #%80
        SBC     R4, R12
        JR      NC, .7
        LD      R6, #%FF
.7:     LD      R13, #%A0
        CALL    M_274E
        JR      .5

M_270E: LD      R4, #0
        LD      R5, R8
M_2712: DECW    R4
        JR      NZ, M_2712
        XOR     %3, #%40
        LD      R5, R8
M_271B: DECW    R4
        JR      NZ, M_271B
        XOR     %3, #%40
        RET

M_2723: LD      R11, #%FF
M_2725: LD      R12, #%FA
M_2727: INC     R11
        LD      R8, %3
        AND     R8, #1
        CP      R8, R4
        JR      Z, M_2725
        INC     R12
        JR      NZ, M_2727
        LD      R4, R8
        RET

M_2737: CALL    M_2723
        CALL    M_2723
M_273D: LD      R10, #8
M_273F: CALL    M_2723
        CALL    M_2723
        CP      R11, #%16
        CCF
        RRC     R9
        DJNZ    R10, M_273F
        RET

M_274E: LD      R8, #%4A
        CALL    M_270E
        DECW    R12
        JR      NZ, M_274E
        LD      R9, R6
        CALL    M_276D
        LD      R13, #%80
        LD      R7, #0
M_2760: LDE     R9, @RR0
        INCW    R0
        CALL    M_276D
        ADD     R7, R9
        DJNZ    R13, M_2760
        LD      R9, R7
M_276D: LD      R8, #%94
        CALL    M_270E
        LD      R10, #8
M_2774: RR      R9
        LD      R8, #%4A
        JR      C, M_277C
        LD      R8, #%25
M_277C: CALL    M_270E
        DJNZ    R10, M_2774
        RET

M_2782: LD      R13, #%50
        LD      R14, #%16
        LD      R15, #%2A
M_2788: CALL    M_27C7
        JR      C, M_2782
        DJNZ    R13, M_2788
        LD      R14, #%2A
        LD      R15, #%6A
M_2793: CALL    M_27C7
        JR      C, M_2793
        CALL    M_27C7
        JR      C, M_2782
        CALL    M_273D
        LD      %5D, R9
        LD      R13, #%80
        LD      R7, #0
        INC     R6
M_27A7: CALL    M_2737
        LDE     @RR0, R9
        INCW    R0
        ADD     R7, R9
        DJNZ    R13, M_27A7
        CALL    M_2737
        LD      %5B, #%70
        CALL    M_20B6
        CP      R7, R9
        JR      NZ, M_27C4
        CP      R6, %5D
        JR      Z, M_27D0
M_27C4: JP      M_0A62

M_27C7: CALL    M_2723
        CP      R11, R14
        JR      C, M_27D0
        CP      R15, R11
M_27D0: RET

M_27D1: CLR     %6
        DEC     %5B
        CALL    M_231F
        DI
        JR      C, M_27DF
        LD      %6, R14
        LD      %7, R15
M_27DF: SRP     #%40
        LD      R0, #%FC
        LD      R1, #0
        LD      R6, #0
        CALL    M_2782
        LD      R2, #%FC
        LD      R3, #%11
        LD      R0, %6
        LD      R1, %7
        OR      R0, R0
        JR      NZ, M_27FB
        LDE     R1, @RR2
        INC     R3
        LDE     R0, @RR2
M_27FB: CALL    M_2782
        JR      M_27FB
