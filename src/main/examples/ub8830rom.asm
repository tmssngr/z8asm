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

        .data %0800
        .data %0803
        .data %0806
        .data %0809
        .data %080c
        .data %080f

boot:
        SRP     #0
        LD      r3, #%0F
        NOP
        TM      r3, #0b0000_0100
        LD      R3, #%FF
        JR      NZ, M_001D
        TM      R3, #0b0000_0100
        JR      NZ, M_003D
M_001D: LD      P01M, #%B6      ; P00-P03 = A8-A11, Stack extern, P1x = AD0-AD7, P04-P04 = A12-A15, extended memory timing
M_0020: LD      P3M, #8         ; P30-P33 input, P35-P37 output, P34 DM, Port 2 open-drain
        LD      R4, #8          ; if %0812 is writable
        LD      R5, #%12
        LDC     R6, @RR4
        COM     R6
        LDC     @RR4, R6
        LDC     R7, @RR4
        COM     R6
        LDC     @RR4, R6
        XOR     R6, R7
        SRP     #%F0
        JP      NZ, %E000       ; jump to %e000
        JP      %0812           ; else jump to %0812

M_003D: LD      P01M, #4        ; P03-03 = output, P04-07 = output, P1x = output, normal timing, intern stack
        LD      TMR, #%C0       ; Timer Mode Register
        LD      R0, #%FF
        LD      R1, #%FF
        RCF
M_0048: AND     R3, #%DF        ; P35 = 0
        OR      R3, #%20        ; P35 = 1
M_004E: RLC     R1
        RLC     R0
        TM      R3, #4          ; if P32 == 0
        JR      Z, M_005B       ; jump
        JR      C, M_004E
        JR      M_0048

M_005B: LD      P01M, #%7F      ; P04-P07 = input, extended timing, P1x = high impedance, internal stack, P00-P03 = A8-A11
M_005E: JR      M_005E          ; endless loop

M_0060: OR      %7F, #0b0001_1000
        LD      P01M, %7F        ; P04-P07 = input, extended timing, P1x = high impedance, internal stack, P00-P03 = A8-A11
        AND     %3, #%DF         ; P35 = 0
M_0069: TM      %3, #0b0000_0100 ; while P32 == 0
        JR      Z, M_0069
        AND     %7F, #0b1111_0111
        OR      %3, #0b0010_0000 ; P35 = 1
        LD      P01M, %7F        ; P04-P07 = input, extended timing, P1x = high impedance, internal stack, P00-P03 = A8-A11
        NOP
        IRET

M_0079: LD      R2, R4
        LD      R3, R5
        RET

        ; +
        ; in:  rr2, rr4
        ; out: rr2
M_007E: ADD     R3, R5
        ADC     R2, R4
        JR      OV, M_009A
        RET

        ; -
        ; in:   rr2, rr4
        ; out:  rr2
M_0085: SUB     R3, R5
        SBC     R2, R4
        JR      OV, M_009A
        RET

        ; ABS
        ; in:   rr4
        ; out:  rr2
M_008C: TM      R4, #%80    ; if bit7(r4) == 0
        JR      Z, M_0079   ;   ld r2w, r4w
M_0091: CLR     R2          ; r2w = 0 - r4w
        CLR     R3
        JR      M_0085

M_0097: OR      %F, #%80
M_009A: OR      %E, #%80
        RET

        ; prepare * or /
        ; in:   rr2, rr4
        ; out:  rr2 = 0
        ;       rr4 = abs(rr4)
        ;       rr6 = abs(rr2)
        ;       r8[7] (xored sign bit)
        ;       r9 = 0
M_009E: LD      R8, R2
        XOR     R8, R4
        LD      R9, #2
M_00A4: LD      R6, R2
        LD      R7, R3
        CALL    M_008C
        LD      R4, R6
        LD      R5, R7
        DJNZ    R9, M_00A4
        LD      R6, R2
        LD      R7, R3
        CLR     R2
        CLR     R3
        RET

        ; *
        ; in:   rr2, rr4
        ; out:  rr2 = product
        ;       (rr4 = abs(product))
        ;       r11 = 0 (if successful)
        ; kill: rr6, rr8, r11
M_00BA: CALL    M_009E
        LD      R11, #%0F
M_00BF: SRA     R6
        RRC     R7
        JR      NC, M_00CB
        ADD     R3, R5
        ADC     R2, R4
        JR      OV, M_0097
M_00CB: RLC     R5
        RLC     R4
        JR      NOV, M_00D5
        OR      R6, R7
        JR      NZ, M_0097
M_00D5: DJNZ    R11, M_00BF
M_00D7: LD      R4, R2
        LD      R5, R3
        RLC     R8
        JR      C, M_0091
        RET

        ; /
M_00E0: CALL    M_009E
        LD      R9, #%10
        RCF
        CLR     R10
        CLR     R11
M_00EA: RLC     R7
        RLC     R6
        RLC     R11
        RLC     R10
        JR      C, M_00FE
        CP      R4, R10
        JR      UGT, M_0103
        JR      C, M_00FE
        CP      R5, R11
        JR      UGT, M_0103
M_00FE: SUB     R11, R5
        SBC     R10, R4
        SCF
M_0103: DJNZ    R9, M_00EA
        LD      R9, R4
        OR      R9, R5
        JR      Z, M_0115
        RLC     R7
        RLC     R6
M_010F: LD      R2, R6
        LD      R3, R7
        JR      M_00D7

M_0115: CALL    M_010F
        OR      %E, #%40
M_011B: OR      %F, #%80
        RET

        ; M(OD)
M_011F: CALL    M_00E0
        LD      R8, R2
        LD      R2, R10
        LD      R3, R11
        JR      M_00D7

        ; O(R)
M_012A: OR      R2, R4
        OR      R3, R5
        RET

        ; A(ND)
M_012F: AND     R2, R4
        AND     R3, R5
        RET

        ; X(OR)
M_0134: XOR     R2, R4
        XOR     R3, R5
        RET

        ; NOT
M_0139: CALL    M_0079
        COM     R2
        COM     R3
        RET

M_0141: AND     %F, #%8F    ; x000_xxxx
        CP      R2, R4
        JR      Z, M_0152
        LD      R7, #%20
        JR      GT, M_014E
M_014C: LD      R7, #%10
M_014E: OR      %F, R7
        RET

M_0152: LD      R7, #%40
        CP      R3, R5
        JR      Z, M_014E
        LD      R7, #%20
        JR      UGT, M_014E
        JR      M_014C

M_015E: LD      R8, R3
        LD      R7, R3
        LD      R6, R2
        LD      R5, R2
        SWAP    R5
        SWAP    R7
        LD      R4, #%25
        LD      R10, #4
M_016E: LD      R11, #%15
        ; nibble to ASCII
M_0170: AND     @R11, #%0F
        ADD     @R11, #%30
        CP      @R11, #%3A
        JR      C, M_017E
        ADD     @R11, #7
M_017E: INC     R11
        DJNZ    R10, M_0170
        RET

        ; intToAscii (decimal)
        ; input: rr2
        ; output: r4 = ' ' or '-'
        ;         r5-r9 = ascii
M_0182: LD      R4, R2
        LD      R5, R3
        CALL    M_008C
        RLC     R4
        LD      R4, #%20    ; ' '
        JR      NC, M_0191
        LD      R4, #%2D    ; '-'
M_0191: LD      R11, #6
        LD      R10, #%15
M_0195: CLR     @R10
        INC     R10
        DJNZ    R11, M_0195
        LD      R11, #%0F
M_019C: SRA     R2
        RRC     R3
        JR      NC, M_01AE
        ADD     R7, R10
        DA      R7
        ADC     R6, R9
        DA      R6
        ADC     R5, R8
        DA      R5
M_01AE: ADD     R10, R10
        DA      R10
        ADC     R9, R9
        DA      R9
        ADC     R8, R8
        DA      R8
        DJNZ    R11, M_019C
        LD      R8, R7
        LD      R9, R7
        LD      R7, R6
        SWAP    R6
        SWAP    R8
        LD      R10, #5
        JR      M_016E

M_01CA: SWAP    @R13
        LD      R14, #4
M_01CE: RLC     @R13
        RLC     R5
        RLC     R4
        RLC     R3
        DJNZ    R14, M_01CE
        RET

M_01D9: LD      R13, #%16
        CLR     R4
        CLR     R5
        CP      R6, #%25    ; '%'
        JR      NZ, M_020B
        LD      R12, #5
M_01E6: INC     R13
        SUB     @R13, #%30
        JR      C, M_0205
        CP      @R13, #%0A
        JR      C, M_01FE
        SUB     @R13, #%11
        JR      C, M_0205
        ADD     @R13, #%0A
        CP      @R13, #%10
        JR      NC, M_0205
M_01FE: CALL    M_01CA
        DJNZ    R12, M_01E6
M_0203: RCF
        RET

M_0205: CP      R12, #5
        JR      Z, M_0203
        RET

        ; BCD to int with sign
M_020B: LD      R12, #6
        CLR     R2
        CLR     R3
        CP      R6, #%2D        ; '-'
        JR      NZ, M_0218
        INC     R2
M_0217: INC     R13
M_0218: SUB     @R13, #%30      ; '0'
        JR      C, M_0229
        CP      @R13, #%0A
        JR      NC, M_0229
        CALL    M_01CA
        DJNZ    R12, M_0217
M_0227: RCF
        RET

        ; BCD to int
M_0229: CP      R12, #6
        JR      Z, M_0227
        CP      R3, #4
        JR      NC, M_0227
        CLR     R6
        CLR     R7
        LD      R11, #%13
        LD      R8, #%27        ; 10,000d
        LD      R9, #%10
        CALL    M_0271
        INC     R11
        LD      R8, #3          ; 1,000d
        LD      R9, #%E8
        CALL    M_026F
        LD      R8, #0          ; 100d
        LD      R9, #%64
        CALL    M_026F
        INC     R11
        LD      R9, #%0A        ; 10d
        CALL    M_026F
        LD      R9, #1          ; 1
        CALL    M_026F
        LD      R4, R6
        LD      R5, R7
        RLC     R6
        JR      C, M_0227
        RRC     R2
        JR      NC, M_027E
        CALL    M_0091
        LD      R4, R2
        LD      R5, R3
        JR      M_027E

M_026F: SWAP    @R11
M_0271: LD      R10, @R11
        AND     R10, #%0F
        JR      Z, M_027E
M_0278: ADD     R7, R9
        ADC     R6, R8
        DJNZ    R10, M_0278
M_027E: SCF
        RET

M_0280: POP     R8
        POP     R9
        POP     R2
        POP     R3
        POP     R6
        POP     R7
        JP      @RR8

       ; SETRR
M_028E: CALL    M_0280
        LD      @R7, R4
        INC     R7
        JR      M_0299

        ; SETR
M_0296: CALL    M_0280
M_0299: LD      @R7, R5
        JP      @RR2

        ; SETEW
M_029D: CALL    M_0280
        LDE     @RR6, R4
        INCW    R6
        JR      M_02A9

        ; SETEB
M_02A6: CALL    M_0280
M_02A9: LDE     @RR6, R5
        JP      @RR2

        ; GETRR
M_02AD: LD      R2, @R5
        INC     R5
        .data %0D              ; skip next 2 byte command (CLR R2)
        ; GETR
M_02B1: CLR     R2
        LD      R3, @R5
        RET

        ; GETEW
M_02B6: LDE     R2, @RR4
        INCW    R4
        .data %0D              ; skip next 2 byte command (CLR R2)
        ; GETEB
M_02BB: CLR     R2
        LDE     R3, @RR4
        RET

M_02C0: CALL    M_02C8
        LD      R5, #%20
        CALL    %0818
M_02C8: LD      R5, #8
        JP      %0818

        ; RL
M_02CD: RCF
        RLC     R5
        RLC     R4
        ADC     R5, #0
M_02D5: JP      M_0079

        ; RR
M_02D8: RCF
        RRC     R4
        RRC     R5
        JR      NC, M_02D5
        OR      R4, #%80
        JR      M_02D5

        ; INPUT
M_02E4: LD      R5, #%3F
        CALL    %0818      ; PTC
M_02E9: LD      R15, #%15
M_02EB: INC     R15
        CP      R15, #%1F
        JR      Z, M_0311
M_02F1: CALL    %0815      ; GTC
M_02F4: LD      @R15, R3
        CP      R3, #%0D
        JR      Z, M_0311
        CP      R3, #8
        JR      NZ, M_0319
        LD      R5, #%20
        CALL    %0818      ; PTC
        DEC     R15
        CP      R15, #%15
        JR      Z, M_02EB
        CALL    M_02C8
        JR      M_02F1

M_0311: CALL    M_01D9
        JR      NC, M_02E4
        JP      M_0079

M_0319: INC     R3
        JR      PL, M_02EB
M_031C: CP      R15, #%16
        JR      Z, M_02E9
        CALL    M_02C0
        DEC     R15
        JR      M_031C

; Daten, gelesen ab M_0400
;  Anzahl Zeichen (0xFF -> Ende)
;  1. Zeichen
;  2. Zeichen
;  ...
;  letztes Zeichen
;  Adresse H
;  Adresse L
M_0328: .data L"NOT"   M_0139
        .data L"ABS"   M_008C
        .data L"SETRR" M_028E
        .data L"SETR"  M_0296
        .data L"SETEW" M_029D
        .data L"SETEB" M_02A6
        .data L"GETRR" M_02AD
        .data L"GETR"  M_02B1
        .data L"GETEW" M_02B6
        .data L"GETEB" M_02BB
        .data L"RL"    M_02CD
        .data L"RR"    M_02D8
        .data L"INPUT" M_02E4
        .data L"GTC"   %0815
        .data L"PTC"   %0818
        .data %FF

M_0391: LD      R15, #%16
        LDC     R6, @RR0
        INCW    R0
        CP      R6, R7
        RET

        ; in:  r6  = char
        ; out: r8w = address
M_039A: LD      R10, #3
        LD      R11, #%C1
        LD      R13, #3
        CALL    M_03AF
        JR      Z, M_03C0
        CP      R6, #%24     ; $
        JR      NZ, M_03C0
        CALL    M_0391
        LD      R13, #3
M_03AF: CALL    M_03B6
        JR      Z, M_03C0
        DJNZ    R13, M_03AF
M_03B6: LD      R12, #%17
        LDCI    @R12, @RR10   ; 1. Byte -> %17/r7
        LDCI    @R12, @RR10   ; 2. Byte -> %18/r8
        LDCI    @R12, @RR10   ; 3. Byte -> %19/r9
        CP      R6, R7
M_03C0: RET

; Daten, gelesen ab M_039A
M_03C1: .data '+' M_007E
        .data '-' M_0085
        .data '*' M_00BA
        .data '/' M_00E0
        .data 'A' M_012F  ; A(ND)
        .data 'O' M_012A  ; O(R)
        .data 'X' M_0134  ; X(OR)
        .data 'M' M_011F  ; M(OD)

        ; isUpperCaseLetter(@r15)
        ; C -> true
M_03D9: CP      @R15, #'A'
        JR      C, M_03F0
        CP      @R15, #'['  ; 'Z'+1
        RET

        ; isLetterOrDigit(@R15)
M_03E2: CALL    M_03D9
        JR      C, M_03EF

        ; isDigit(@r15)
        ; C -> true
M_03E7: CP      @R15, #%30
        JR      C, M_03F0
        CP      @R15, #%3A
M_03EF: RET

M_03F0: RCF
        RET

M_03F2: CALL    M_03E7
        JR      C, M_03EF
        CP      @R15, #%41
M_03FA: JR      C, M_03F0
        CP      @R15, #%47
        RET

M_0400: LD      R2, #3      ; %328
        LD      R3, #%28
        CALL    M_0412
        JR      C, M_041E
        CLR     R2
        OR      R2, %8
        JR      Z, M_041D
        LD      R3, %9
M_0412: LD      R4, R0
        LD      R5, R1
M_0416: LDC     R8, @RR2
        CP      R8, #%FF
        JR      NZ, M_041F
M_041D: RCF
M_041E: RET

M_041F: INCW    R2
        LDC     R7, @RR2
        CALL    M_0391
        JR      NZ, M_043C
        DJNZ    R8, M_041F
        LDC     R6, @RR0
        CALL    M_03E2
        JR      C, M_043B
        INCW    R2
        LDC     R8, @RR2
        INCW    R2
        LDC     R9, @RR2
        SCF
        RET

M_043B: INC     R8
M_043C: INC     R8
        INC     R8
        ADD     R3, R8
        ADC     R2, #0
        LD      R0, R4
        LD      R1, R5
        JR      M_0416

M_0449: DECW    R0
M_044B: CALL    M_0400
        LDC     R10, @RR0
        CP      R10, #%5B
        JR      NZ, M_046F
        INCW    R0
M_0457: PUSH    R8
        PUSH    R9
        CALL    M_04C7
        POP     R9
        POP     R8
        LD      R7, #%5D
        CALL    M_0391
        JR      Z, M_046F
        PUSH    R3
        PUSH    R2
        JR      M_0457

M_046F: LD      R4, R2
        LD      R5, R3
        CALL    @RR8
        SRP     #%10
        SCF
        RET

M_0479: CALL    M_03D9
        JR      NC, M_0495
        LDC     R7, @RR0
        INC     R15
        CALL    M_03E2
        JR      C, M_0449
        SUB     R6, #%41
        RL      R6
        ADD     R6, #%20
        LD      R2, @R6
        INC     R6
        LD      R3, @R6
        SCF
        RET

M_0495: CP      @R15, #%3B      ; ';'
        JR      Z, M_04B3
        CP      @R15, #%0D
        JR      Z, M_04B3
        INC     R15
M_04A0: LDCI    @R15, @RR0
        DEC     R15
        CALL    M_03F2
        INC     R15
        JR      C, M_04A0
        DECW    R0
        CALL    M_01D9
        LD      R2, R4
        LD      R3, R5
M_04B3: RET

M_04B4: LD      R7, #%28        ; '('
        CALL    M_0391
        JR      NZ, M_0479
        CALL    M_04C7
        JR      NC, M_04C6
        LD      R7, #%29        ; ')'
        CALL    M_0391
        CCF
M_04C6: RET

M_04C7: CALL    M_04B4
        JR      NC, M_04D5
M_04CC: CALL    M_0391
        CALL    M_039A
        JR      Z, M_04D8
        SCF
M_04D5: DECW    R0
        RET

M_04D8: PUSH    R8
        PUSH    R9
        PUSH    R2
        PUSH    R3
        CALL    M_04B4
        LD      R4, R2
        LD      R5, R3
        POP     R3
        POP     R2
        POP     R9
        POP     R8
        CALL    @RR8
        JR      M_04CC

        ; getRegisterForVariable
M_04F3: CALL    M_0391
        SUB     R6, #%41
        ADD     R6, R6
        LD      R8, #%20
        ADD     R8, R6
        RET

M_0500: LDC     R4, @RR0
        DECW    R0
        TM      R4, #%80
        JR      Z, M_0500
        INCW    R0
        AND     R4, #%7F
        CP      R0, %6
        JR      NZ, M_0519
        CP      R1, %7
        SCF
        JR      Z, M_0528
M_0519: DECW    R0
        LDC     R5, @RR0
        INCW    R0
        CP      R5, #%0D
        JR      Z, M_0528
        DECW    R0
        JR      M_0500

M_0528: INCW    R0
        LDC     R5, @RR0
        DECW    R0
        RET

        ; printQuotedString
M_052F: LD      R7, #%22        ; '"'
        CALL    M_0391
        JR      NZ, M_0542
M_0536: CALL    M_0391
        JR      Z, M_0544
        LD      R5, R6
        CALL    %0818
        JR      M_0536

M_0542: DECW    R0
M_0544: RET

M_0545: CALL    M_04C7
        CLR     R10
        LD      R9, #2
M_054C: LD      R8, #%10
        LDC     R6, @RR0
        CP      R6, #%3C        ; '<'
        JR      Z, M_0563
        RL      R8
        CP      R6, #%3E        ; '>'
        JR      Z, M_0563
        RL      R8
        CP      R6, #%3D        ; '='
        JR      NZ, M_0569
M_0563: OR      R10, R8
        INCW    R0
        DJNZ    R9, M_054C
M_0569: PUSH    R10
        PUSH    R2
        PUSH    R3
        CALL    M_04C7
        LD      R4, R2
        LD      R5, R3
        POP     R3
        POP     R2
        CALL    M_0141
        POP     R8
        AND     R8, %0F
        RET

        ; REM
M_0583: LD      R7, #%3B        ; ';'
M_0585: CALL    M_0391
        JR      Z, M_0590
        CP      R6, #%0D
        SCF
        JR      NZ, M_0585
M_0590: DECW    R0
M_0592: RET

M_0593: CALL    M_0583
        JR      C, M_0592
        INCW    R0
        JR      M_0593

        ; LET
M_059C: CALL    M_04F3
        PUSH    R8
        INCW    R0
        CALL    M_04C7
        POP     R8
M_05A8: LD      @R8, R2
        INC     R8
        LD      @R8, R3
        RET

        ; TRAP
M_05AE: LD      %4, R0
        LD      %5, R1
M_05B2: JR      M_0583

        ; PROC
M_05B4: PUSH    %4
        PUSH    %5
        LD      %4, R0
        LD      %5, R1
        LDC     R6, @RR0
        CP      R6, #%5B        ; '['
        JR      NZ, M_05D8
        INCW    R0
        CLR     R8
        LD      R7, #%5D        ; ']'
M_05C9: INCW    R0
        CALL    M_0391
        JR      Z, M_05D6
        PUSH    R8
        PUSH    R8
        JR      M_05C9

M_05D6: INCW    R0
M_05D8: CALL    M_044B
        LD      R0, %4
        LD      R1, %5
        LD      R7, #%5B
        CALL    M_0391
        JR      NZ, M_05FE
        LD      R7, #%5D
M_05E8: CALL    M_04F3
        CALL    M_0391
        JR      Z, M_05FB
        POP     R10
        LD      @R8, R10
        POP     R10
        INC     R8
        LD      @R8, R10
        JR      M_05E8

M_05FB: CALL    M_05A8
M_05FE: POP     %5
        POP     %4
        JR      M_05B2

        ; GOTO
M_0604: CALL    M_04C7
        CALL    M_0500
        CALL    M_0141
        TM      %F, #%50
        JR      NZ, M_0631
M_0612: CALL    M_0593
        INCW    R0
        LDC     R4, @RR0
        INCW    R0
        LDC     R5, @RR0
        DECW    R0
        RL      R4
        RCF
        RRC     R4
        JR      NC, M_062E
        CALL    M_0141
        TM      %F, #%50
        JR      Z, M_0612
M_062E: DECW    R0
        RET

M_0631: CALL    M_0500
        JR      NC, M_063D
        INCW    SPH
        INCW    SPH
        JP      M_073E

M_063D: CALL    M_0141
        TM      %F, #%60
        JR      NZ, M_062E
        DECW    R0
        JR      M_0631

        ; IF
M_0649: AND     %F, #%FE
        CALL    M_0545
        JR      NZ, M_0656
        INC     %F
        JP      M_0593

M_0656: RET

        ; ELSE
M_0657: TM      %F, #1
        JP      Z, M_0593
        AND     %F, #%FE
        RET

        ; RETURN
M_0661: CP      %E, #0
        JR      NZ, M_066C
        OR      %E, #%20
M_0669: JP      M_011B

M_066C: DEC     %E
        POP     R6
        POP     R7
        POP     R0
        POP     R1
        AND     %F, #%FE
        JP      @RR6

        ; GOSUB
M_067B: LD      R8, R0
        LD      R9, R1
        CALL    M_0583
        POP     R10
        POP     R11
        PUSH    R1
        PUSH    R0
        PUSH    R11
        PUSH    R10
        LD      R0, R8
        LD      R1, R9
M_0692: INC     %E
        TM      %E, #%10
        JP      Z, M_0604
        CALL    M_0583
        JR      M_0669

        ; WAIT
M_069F: CALL    M_04C7
        LD      R6, R2
        OR      R6, R3
        JR      Z, M_06B8
M_06A8: DA      R6
M_06AA: LD      R6, #0
        LD      R7, #%B4
M_06AE: DECW    R6
        JP      NZ, M_06AE
        DECW    R2
        JP      NZ, M_06A8
M_06B8: RET

        ; CALL
M_06B9: CALL    M_04C7
        CALL    @RR2
        SRP     #%10
        RET

        ; STOP
M_06C1: OR      %F, #8
        RET

        ; END
M_06C5: OR      %F, #2
        RET

        ; TOFF
M_06C9: CLR     %4
        CLR     %5
        RET

        ; PTH
M_06CE: CALL    M_052F      ; printQuotedString
        CALL    M_04C7
        JR      NC, M_06F8
        CALL    M_015E
        LD      R10, #5
        JR      M_06EA

        ; PRINT
M_06DD: CALL    M_052F      ; printQuotedString
        CALL    M_04C7
        JR      NC, M_06F8
M_06E5: CALL    M_0182      ; intToAscii
        LD      R10, #6
M_06EA: LD      R11, #%14
M_06EC: PUSH    R5
        LD      R5, @R11
        INC     R11
        CALL    %0818
        POP     R5
        DJNZ    R10, M_06EC
M_06F8: LDC     R6, @RR0
        CP      R6, #%2C
        JR      NZ, M_0710
        INCW    R0
        LDC     R6, @RR0
        CP      R6, #%3B
        JR      Z, M_070F
        CP      R6, #%0D
        JR      Z, M_070F
        DECW    R0
M_070F: RET

M_0710: LD      R5, #%0D
        JP      %0818

        ; INPUT
M_0715: CALL    M_052F      ; printQuotedString
        CALL    M_02E9
        CALL    M_04F3
        JP      M_05A8

        LD      %F, #4
        JR      M_0736

        LD      %F, #8
        JR      M_0736

M_072B: CLR     %F
        CLR     %E
        LD      R0, %6
        LD      R1, %7
        CALL    M_06C9
M_0736: SRP     #%10
        POP     %A
        POP     %B
        JR      M_0748

M_073E: TM      %F, #%0A
        JR      NZ, M_074D
        TCM     %F, #%84
        JR      Z, M_074D
M_0748: LDC     R6, @RR0
        INC     R6
        DJNZ    R6, M_074F
M_074D: JP      @%0A

M_074F: CP      %6, R0
        JR      NZ, M_0759
        CP      %7, R1
        JR      Z, M_0785
M_0759: LD      R6, %4
        OR      R6, %5
        JR      Z, M_0785
        PUSH    R1
        PUSH    R0
        LD      R0, %4
        LD      R1, %5
        CALL    M_0545
        JR      Z, M_0781
        CALL    M_06C9
        POP     R6
        POP     R7
        DECW    R6
        PUSH    R7
        PUSH    R6
        INCW    R0
        CALL    M_0692
        JR      M_07B9

M_0781: POP     R0
        POP     R1
M_0785: INCW    R0
        INCW    R0
M_0789: LDC     R3, @RR0
        INCW    R0
        CP      R3, #%3E
        JR      Z, M_0795
        AND     %0F, #%FE
M_0795: LD      R6, #7
        LD      R7, #%C8
        CLR     R2
M_079B: LDC     R8, @RR6
        CP      R8, R3
        JR      Z, M_07A6
        INCW    R6
        INC     R2
        JR      M_079B

M_07A6: ADD     R2, R2
        LD      R6, #7
        LD      R7, #%D9
        ADD     R7, R2
        ADC     R6, #0
        LD      R2, #%0C
        LDCI    @R2, @RR6
        LDCI    @R2, @RR6
M_07B7: CALL    @%0C
M_07B9: LD      R7, #%0D
        CALL    M_0391
        JP      Z, M_073E
        CP      R6, #%3B
        JR      Z, M_0789
        JR      M_07B7

        ; data read from M_0795
M_07C8: .data 'L' ; let
        .data 'O' ; proc
        .data 'G' ; goto
        .data 'F' ; if
        .data '>' ; else
        .data 'R' ; return
        .data 'S' ; gosub
        .data 'W' ; wait
        .data 'M' ; rem
        .data 'C' ; call
        .data 'T' ; stop
        .data 'E' ; end
        .data '!' ; trap
        .data '/' ; toff
        .data 'P' ; print
        .data 'H' ; pth
        .data 'I' ; input

        ; data read from M_07A6
        ; address
M_07D9: .data M_059C ; LET
        .data M_05B4 ; PROC
        .data M_0604 ; GOTO
        .data M_0649 ; IF
        .data M_0657 ; ELSE
        .data M_0661 ; RETURN
        .data M_067B ; GOSUB
        .data M_069F ; WAIT
        .data M_0583 ; REM
        .data M_06B9 ; CALL
        .data M_06C1 ; STOP
        .data M_06C5 ; END
        .data M_05AE ; TRAP
        .data M_06C9 ; TOFF
        .data M_06DD ; PRINT
        .data M_06CE ; PTH
        .data M_0715 ; INPUT
        .data %ffff
        jp  M_072B
