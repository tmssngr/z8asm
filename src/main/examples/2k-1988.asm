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

        JP      %E000
        JP      %E003
        JP      %E006
        JP      %E009
        JP      M_0ADA          ; fires each 64us
        JP      %E00F

        ; boot
M_0812: JP      M_08FF

M_0815: JP      M_081D

M_0818: LD      %5A, %15
        JR      M_0827

M_081D: CALL    M_0824
        LD      %13, %5A
        RET

M_0824: CALL    M_0C1D
        ; print char in %5A
M_0827: CP      %5A, #%7F
        JR      Z, M_086B
        CP      %5A, #%20       ; space
        JR      NC, M_0872
        CP      %5A, #%1B       ; cursor right
        JR      Z, M_0875
        LD      %5C, #%F0
        CP      %5A, #%1A       ; cursor up
        JR      Z, M_0878
        LD      %5C, #%10
        CP      %5A, #%0A       ; cursor down
        JR      Z, M_0878
        LD      %5C, #%FF
        CP      %5A, #%0B       ; cursor left
        JR      Z, M_0878
        CP      %5A, #8
        JR      NZ, M_0860
        CALL    M_0878
        LD      %5A, #%20
        CALL    M_0B95
        LD      %5A, #8
        RET

M_0860: CP      %5A, #%0D       ; Enter
        JR      NZ, M_086C
        CALL    M_0AD4
        LD      %5A, #%0D
M_086B: RET

M_086C: CP      %5A, #%0C       ; CLS
        JR      Z, M_08DD
        RET

        ; any char entered >= ' '
M_0872: CALL    M_0B95
M_0875: LD      %5C, #1
M_0878: ADD     %5B, %5C
        JR      OV, M_0899
        JR      MI, M_08FC
        LD      %60, %5C
        LD      %5C, %5B
        AND     %5C, #%0F
        CP      %5C, #%0D
        JR      C, M_08DC
        RL      %60
        JR      NC, M_0894
        SUB     %5B, #6
M_0894: ADD     %5B, #3
        JR      NOV, M_08DC
M_0899: PUSH    RP
        SRP     #%60
        LD      R0, #%FD
        LD      R1, #0
        LD      R2, #%FD
        LD      R3, #%10
        LD      R4, #%70
M_08A7: LDE     R5, @RR2
        LDE     @RR0, R5
        INC     R1
        INC     R3
        DJNZ    R4, M_08A7
        LD      R4, #%10
        LD      R5, #%20
M_08B3: LDE     @RR0, R5
        INC     R1
        DJNZ    R4, M_08B3
        LD      R1, #%38
        INC     R2
        INC     R0
        LD      R3, #%70
        LD      %5B, R3
M_08C0: LDE     R5, @RR2
        LDE     @RR0, R5
        INCW    R0
        INCW    R2
        CP      R3, #%F0
        JR      C, M_08C0
        LD      R5, #%FF
        CP      R2, R5
        JR      C, M_08C0
        LD      R4, #%38
M_08D5: LDE     @RR0, R5
        INC     R1
        DJNZ    R4, M_08D5
        POP     RP
M_08DC: RET

        ; CLS?
M_08DD: PUSH    RP
        SRP     #%60
        LD      R4, #%FD        ; ldw r4, #FD80
        LD      R5, #%80
        LD      R3, #%20
        LD      R2, #%80
M_08E9: DEC     R5
        LDE     @RR4, R3
        DJNZ    R2, M_08E9
        LD      R2, #2
        LD      R3, #%FF
M_08F3: INC     R4
M_08F4: LDE     @RR4, R3
        DJNZ    R5, M_08F4
        DJNZ    R2, M_08F3
        POP     RP
M_08FC: CLR     %5B
        RET

        ; boot
M_08FF: SRP     #%F0
        LD      R15, #0         ; SP = %FE00
        LD      R14, #%FE
        LD      R11, #%10       ; FB = IMR
        LD      R9, #8          ; F9 = IPR
        LD      R8, #%92        ; F8 = P01M
        LD      R5, #5          ; F5 = PRE0
        LD      R4, #%40        ; F4 = T0
        LD      R1, #3          ; F1 = TMR
        LD      %6, #%E0        ; ldw %06, #E000
        CLR     %7
        CLR     %8
        CLR     %6D
        CALL    M_08DD
        EI
        LD      %58, #%14
        CALL    M_0BF2
M_0924: SRP     #%10
        LD      %5A, #'K'
        CALL    M_0B95
        CALL    M_0824
        LD      R6, %5A
        LD      R15, #%16
        CALL    %03D9           ; isUpperCaseLetter(@R15)?
        JR      NC, M_094D      ; no
        SUB     R6, #%31        ; yes, print variable value
        ADD     R6, R6
        LD      R2, @R6
        INC     R6
        LD      R3, @R6
        LD      %5A, #%3D
        CALL    M_0872
        CALL    M_0E92
        JR      M_0924
        ; no upper case character entered
M_094D: CP      R6, #'*'        ; * ... Continue
        JR      NZ, M_095C
        TM      %0F, #8
        JR      Z, M_098D
        AND     %0F, #%F7
        JR      M_0978

M_095C: LD      %0F, #4
        CLR     %0E
        LD      R0, %6
        LD      R1, %7
        CP      R6, #8          ; tab?
        JR      NZ, M_0970
        LD      R4, #0
        LDE     @RR0, R4
        JR      M_0924

M_0970: CP      R6, #'+'        ; + ... Run
        JR      NZ, M_09AB
        CALL    %06C9
M_0978: CALL    %0738
        CALL    M_0AD4
        LD      %58, #%0C
        TM      %0F, #2
        JR      NZ, M_0992
        DEC     %58
        TM      %0F, #8
        JR      NZ, M_0992
M_098D: CALL    M_0E80
M_0990: JR      M_0924
M_0992: CALL    M_0BF2
        PUSH    R0
        PUSH    R1
        DECW    R0
        CALL    %0500
        LD      R2, R4
        LD      R3, R5
        CALL    M_0E92
        POP     R1
        POP     R0
        JR      M_0990

M_09AB: CP      R6, #'-'        ; - ... List
        LD      %6E, #%0C
        JR      NZ, M_09D9
        LD      %6F, #%E6
M_09B6: LDE     R2, @RR0
        OR      R2, R2
        JR      Z, M_0990
        INCW    R0
        LDE     R3, @RR0
        AND     R2, #%7F
        CALL    M_0AA3
        CALL    M_0DCC
        JR      NC, M_098D
        CLR     %6D
        CALL    M_0C1D
        CP      %5A, #%2D
        JR      NZ, M_0990
        INCW    R0
        JR      M_09B6

M_09D9: CALL    %03E7           ; isDigit
        JR      NC, M_0A38      ; no
        LD      R3, R6          ; yes
        CALL    %02F4
        LD      R15, #%16
        LD      R8, #%80
        OR      R8, R4
        LD      R9, R5
        OR      R9, R5
        JR      NZ, M_09F0
        INC     R9
M_09F0: LDE     R2, @RR0
        OR      R2, R2
        JR      Z, M_0A13
        AND     R2, #%7F
        INCW    R0
        LDE     R3, @RR0
        CALL    %0141
        TM      %0F, #%40
        JR      NZ, M_0A33
        TM      %0F, #%20
        JR      NZ, M_0A11
        CALL    %0593
        INCW    R0
        JR      M_09F0

M_0A11: DECW    R0
M_0A13: LD      %6F, #%D7
        LD      R6, R8
        CALL    M_0CA4
        LD      R6, R9
        CALL    M_0DCC
        JR      C, M_0A2D
        CALL    M_0A81
        INC     R6
        JR      MI, M_0A30
        CALL    M_0E80
        JR      M_0A30

M_0A2D: CALL    M_0CA4
M_0A30: JP      M_0924

M_0A33: CALL    M_0A81
        JR      M_0A13

M_0A38: DI
        LD      PRE0, #%05      ; 001000 0 1
                                ;      |   +-- repeating
                                ;      +------ prescaler = 1
        LD      T0, #%0D        ; max = 13
        ; -> 4800 baud

        LD      TMR, #%43       ; 01 00 0 0 1 1
                                ;  |  | | | | +-- load T0
                                ;  |  | | | +---- enable T0
                                ;  |  | | +------ don't load T1
                                ;  |  | +-------- disable T1
                                ;  |  +---------- external clock input at P31
                                ;  +------------- T0 out at P36
        LD      P3M, #%48       ; 0 1 0 01 0 0 0
                                ; | | |  | |   +-- Port 2 open-drain
                                ; | | |  | +------ P32=input P35=output
                                ; | | |  +-------- P33=Input P34 = DM
                                ; | | +----------- P31=Input (TIN), P36=Output(TOUT)
                                ; | +------------- P30=Serial In, P37=Serial Out
                                ; +--------------- No Parity
        LD      SIO, #%FF
        CLR     IRQ
        CP      R6, #'_'        ; shift O ... Load
        JR      NZ, M_0A63
        ; load:
.1:     TM      IRQ, #8         ; IRQ3= serial byte received
        JR      Z, .1
        CLR     IRQ
        LD      R6, SIO
        LDE     @RR0, R6
        INCW    R0
        OR      R6, R6
        JR      NZ, .1
M_0A60: JP      %0020

M_0A63: CP      R6, #'@'        ; shift P ... Save
        JR      NZ, M_0A60
        ; save:
        LD      R2, #%14
        CALL    %06AA           ; wait ca 2M internal clock cycles (~5s)
        INC     R6              ; after the wait, it was zero, so it become != 0 now
.1:     TM      IRQ, #%10       ; IRQ4= serial byte sent
        JR      Z, .1
        CLR     IRQ
        OR      R6, R6
        JR      Z, M_0A60       ; loop until a 0x00 has been sent
        LDE     R6, @RR0
        LD      SIO, R6
        INCW    R0
        JR      .1

M_0A81: CALL    %0500
        LD      R2, R0
        LD      R3, R1
        LD      R4, R0
        LD      R5, R1
        INCW    R4
M_0A8E: INCW    R4
        LDE     R7, @RR4
        RL      R7
        JR      UGT, M_0A8E
M_0A96: LDE     R7, @RR4
        LDE     @RR2, R7
        INCW    R2
        INCW    R4
        OR      R7, R7
        JR      NZ, M_0A96
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

        ; interrupt routine
M_0ADA: PUSH    RP
        SRP     #%50
        DJNZ    R4, M_0B3E
        LD      R6, SPH
        LD      R7, SPL
        LD      SPH, #%FE
        CLR     SPL
        LD      R4, #%40
M_0AEB: SWAP    %80             ; bug? 8 cycles
        OR      %3, #%80        ; P37 = 1, positive sync pulse 2.5us (10 cycles)
        AND     %3, #%7F        ; P37 = 0   10 cycles
        TM      %6D, #%80       ;           10 cycles
        JR      Z, M_0AFD       ;           12 cycles
        XOR     %3, #%40        ; flip P36; 10 cycles
        JR      M_0B02          ;           12 cycles

M_0AFD: AND     %3, #%3F        ; P36 = P37 = 0; 10 cycles
M_0B00: JR      F, M_0B00       ; 10 cycles
M_0B02: JR      F, M_0B02       ; -"-
        NOP                     ; 6 cycles
        POP     %80             ; 10 cycles, read FE00
        NOP
        POP     %80             ; read FE01
        NOP
        POP     %80             ; read FE02
        NOP
        POP     %80             ; read FE03
        NOP
        POP     %80             ; read FE04
        NOP
        POP     %80             ; read FE05
        NOP
        POP     %80             ; read FE06
        NOP
        POP     %80             ; read FE07
        INC     R5
        JR      Z, M_0B27
        SUB     SPL, #8
        SBC     SPH, #0
        JR      M_0AEB          ; 1 loop is 256 cycles = 64us
                                ; 3 loops per row

M_0B27: LD      R5, #%FD
        NOP                     ; 6 cycles
        NOP
        DJNZ    R4, M_0AEB
        AND     IRQ, #%EF       ; 0bxxx0_xxxx
        OR      %3, #%80        ; P37 = 1 (positive sync pulse)
        AND     %3, #%7F        ; P37 = 0
        LD      SPH, R6
        LD      SPL, R7
        LD      R4, #%78        ; 126d
        JR      M_0B5A

M_0B3E: CP      R4, #%20
        JR      C, M_0B50       ; < 32d
        CP      R4, #%25
        JR      NC, M_0B54      ; >= 37d
        AND     %3, #%7F        ; P37=0 (negative sync pulse)
        OR      %3, #%80        ; P37=1
        JR      M_0B5A

M_0B50: INCW    %56
        DECW    %56
M_0B54: OR      %3, #%80        ; P37 = 1 (positive sync pulse
        AND     %3, #%7F        ; P37 = 0
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

        ; print char at position
        ; input: R5A character
        ;        R5B position
M_0B95: PUSH    RP
        SRP     #%60
        LD      R0, #%FD        ; ldw r0, #FD00+R5B
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

        ; print R58-th string from 0E98
M_0BF2: PUSH    RP
        SRP     #%50
        LD      R14, #%0E       ; ldw rE, #%0E98
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

        ; read keyboard waiting with caret
        ; out: %5A = ascii
M_0C1D: CP      %54, #%77
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
M_0C3C: CP      %54, #%77
        JR      NZ, M_0C3C
        CALL    M_0B95
        CALL    M_0C56          ; read keyboard
        TM      %6D, #%7F
        JR      Z, M_0C1D
        LD      %5A, %6D
        AND     %6D, #%80
        AND     %5A, #%7F
        RET

        ; read keyboard (non-waiting)
        ; out: %6D = ascii + 0x80
M_0C56: PUSH    RP
        SRP     #%60
        LD      R0, #%7F        ; ldw r0, #7f0f
        LD      R1, #%0F
M_0C5E: LDC     R2, @RR0
        AND     R2, #%F0        ; 0bxxxx_0000
        JR      NZ, M_0C6C
        DJNZ    R1, M_0C5E
        AND     R13, R0         ; 6D = 0b0xxx_xxxx
M_0C69: POP     RP
        RET

M_0C6C: OR      R13, R13        ; 6D != 0?
        JR      NZ, M_0C69
        LD      R13, #%80       ; 6D = #80
        LD      R3, #%40
M_0C74: SUB     R3, #%10
        RL      R2
        JR      NC, M_0C74
        OR      R1, R3
        LD      R0, #%0F
        LDC     R3, @RR0        ; read char from keyboard layout map
        LD      R0, #%FF
        LD      R1, #%FF
        LDE     R2, @RR0        ; read %FFFF
        CP      R3, #%15        ; == Shift?
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

M_0DBA: .data %02 %27 %0D %37 %23 %23
        .data %0D %0D %27 %0D %23 %23
        .data %3A %23 %60 %60 %50 %FF

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

        LD      R5, #0
        JP      F, %5C01
        CALL    M_0CF4
        JR      NC, M_0DEB
        OR      R5, R5
        JR      NZ, M_0E0D
        LD      R6, #%3B
        LD      %58, #%13
        CALL    M_0BF2
M_0E4D: JP      M_0DCC

        CALL    @%6E
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
        .data   %00 %0B "QWERTZUIOP+" %0B %00 %00
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
