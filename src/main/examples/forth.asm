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

        .ORG    %E000

        ; rr2 = top value of data stack (TOS)
        ; r15 = pointer to data stack (in registers)
        ;       precise: pointer to the second data stack entry

M_E000:
        ; header for invocation from basic
        .data   %8b %08     ; JR      %E00A
        .data   "C%E00A\r" %00
M_E00A:
        JP      M_E0C7

M_E00D:
        CALL    M_E208          ; popIntoRr4
        LD      R8, R2
        XOR     R8, R4
        OR      R2, R2
        JR      PL, M_E01E
        COM     R2
        COM     R3
        INCW    R2
M_E01E: OR      R4, R4
        JR      PL, M_E028
        COM     R4
        COM     R5
        INCW    R4
M_E028: LD      R9, #%10
        LD      R10, #0
        LD      R11, #0
M_E02E: RRC     R4
        RRC     R5
        JR      NC, M_E038
        ADD     R11, R3
        ADC     R10, R2
M_E038: RRC     R10
        RRC     R11
        RRC     R6
        RRC     R7
        DJNZ    R9, M_E02E
        LD      R2, R10
        LD      R3, R11
        RET

M_E047: CALL    M_E208          ; popIntoRr4
        LD      R8, R2
        XOR     R8, R4
        OR      R4, R4
        JR      PL, M_E058
        COM     R4
        COM     R5
        INCW    R4
M_E058: LD      R10, R4
        LD      R11, R5
        CALL    M_E208          ; popIntoRr4
        OR      R4, R4
        JR      PL, M_E071
        COM     R4
        COM     R5
        COM     R2
        COM     R3
        INCW    R2
        JR      NZ, M_E071
        INCW    R4
M_E071: CLR     R6
        CLR     R7
        LD      R9, #%20
        RLC     R3
        RLC     R2
        RLC     R5
        RLC     R4
M_E07F: RLC     R7
        RLC     R6
        SUB     R7, R11
        SBC     R6, R10
        JR      NC, M_E08D
        ADD     R7, R11
        ADC     R6, R10
M_E08D: CCF
        RLC     R3
        RLC     R2
        RLC     R5
        RLC     R4
        DJNZ    R9, M_E07F
        RET

        ; pushRr4
M_E099: CALL    M_E0A7          ; prepareForPushOrError
        LD      @R15, R2        ; store rr2 (TOS) on data stack
        DEC     R15
        LD      @R15, R3
        LD      R2, R4          ; make rr4 to TOS
        LD      R3, R5
        RET

        ; prepareForPushOrError
M_E0A7: DEC     R15             ; check for available data stack space
        LD      R6, #%37        ; if full, show "#MSG 7"
        CP      R15, #%21
        JP      C, M_E0B2
        RET

M_E0B2: CALL    M_E221 ; printString
        .data   %06
        .data   "\r#MSG "
        NOP
        LD      %5A, R6
        CALL    %0827
        CALL    %0AD4
        JR      M_E0DD

        ; start
M_E0C7:
        SRP     #%10
        CALL    M_E221 ; printString
        .data   %10 %0C
        .data   " JU+TE-FORTH\r\n\r"
M_E0DD: LD      R15, #%50
        DI
        SRP     #%F0
        LD      R15, #0
        LD      R14, #%FE
        LD      R11, #%10
        LD      R9, #8
M_E0EA: LD      R8, #%92
        LD      R7, #0
        LD      R5, #7
        LD      R4, #%40
        LD      R1, #3
        SRP     #%10
        LD      %7C, #%0A
        LD      %7D, #%DA
        EI
M_E0FD: SRP     #%10
        CALL    M_E221          ; printString
        .data   %04
        .data   " OK\r"
        CALL    M_E23D          ; QUERY
        CALL    M_E6D7
        JP      M_E0FD

M_E110: LD      R2, R4
        LD      R3, R5
M_E114: RET

        ; [rr4] = 0
M_E115: LD      R8, #0
        LDE     @RR4, R8
        RET

M_E11A: CALL    M_E099          ; pushRr4
        LD      R10, #%14
        RET

M_E120: CALL    M_E208          ; popIntoRr4
        LD      R6, R2
        LD      R7, R3
        SUB     R7, R5
        SBC     R6, R4
        RET

        ; ( d c b a -- )
        ; load 4 data stack entries into rr10, rr8, rr4 and rr2
M_E12C: CALL    M_E208          ; popIntoRr4  (rr2 = b, rr4 = a)
        LD      R8, R2          ; rr8 = b
        LD      R9, R3
        LD      R10, R4         ; rr10 = a
        LD      R11, R5
        CALL    M_E208          ; popIntoRr4  (rr2 = c, rr4 = b)
        JP      M_E208          ; popIntoRr4  (rr2 = d, rr4 = c)

M_E13D: CALL    M_E5EA
        CALL    M_E603
        LD      R12, R2
        LD      R13, R3
        JP      M_E208          ; popIntoRr4

M_E14A: CALL    M_E9FD
        JP      M_E5F4

        ; negate ( x -- -x )
M_E150: COM     R2
        COM     R3
        INCW    R2
        RET

M_E157: CALL    M_E12C
        SUB     R3, R9
        SBC     R2, R8
        SBC     R5, R11
        SBC     R4, R10
        RET

M_E163: CALL    M_E157
        CLR     R2
        CLR     R3
        RET

M_E16B: CALL    M_E9FD
        CALL    M_E5F4
        LD      R4, R2
        OR      R4, R3
        LD      R6, #%44
        JP      Z, M_E0B2
        JP      M_E198

M_E17D: CLR     %6D
        CALL    %0C56
        JR      NZ, M_E17D
        CLR     %6D
        JP      M_E099          ; pushRr4

M_E189: CALL    M_EC7D
        JP      M_EC3E

M_E18F: LD      %5A, #%22
        CALL    %0827
        JP      M_EC3E

M_E198: CALL    M_E208          ; popIntoRr4
        JP      M_E208          ; popIntoRr4

        .repeat 77
          .data %00
        .end

        .data   "69135"
        .data   "79691-20121-F412"

M_E200: .data   %84
        .data   "DROP"
        .data   %00 %00 %00

        ; popIntoRr4
M_E208: LD      R4, R2
        LD      R5, R3
        LD      R3, @R15
        INC     R15
        LD      R2, @R15
        ; prepareForDropCheckEmpty
M_E211: INC     R15
        LD      R6, #%31
        CP      R15, #%51       ; if empty, show #MSG 1
        JP      NC, M_E0B2
        RET

M_E21B: .data   %82
        .data   ".\""
        .data   M_E200 %0e

        ; printString
        ; call printString
        ; n
        ; c1 ... cn
M_E221: POP     R4
        POP     R5
        LDC     R8, @RR4
        INCW    R4
M_E229: LD      R7, #%5A
        LDCI    @R7, @RR4
        CALL    %0827
        DJNZ    R8, M_E229
        JP      @RR4

M_E234: .data   %85
        .data   "QUERY"
        .data   M_E21B %00

M_E23D: LD      %5A, #%5C       ; '\'
        CALL    %0827
M_E243: CALL    %0824
        CP      %5A, #%0D       ; '\r'
        JR      NZ, M_E243
        LD      R4, #%FD
        LD      R5, %5B
        INC     R5
        CALL    M_E115          ; [rr4] = 0
M_E253: LD      R6, #%30
        OR      R5, R5
        JP      Z, M_E0B2
        DEC     R5
        LDE     R6, @RR4
        CP      R6, #%5C        ; '\'
        JR      NZ, M_E253
        LD      R6, #%20
        LDE     @RR4, R6        ; [rr4] = ' '
        CALL    M_E099          ; pushRr4 -> push address of entered string
        CALL    M_E277          ; TIB
        JP      M_E281          ; @TIB = rr4

M_E270: .data   %83
        .data   "TIB"
        .data   M_E234 %00

        ; put the (address) %E27A onto the data stack
M_E277: CALL    M_E294
M_E27A: .data   %FD %71
M_E27C: .data   %81 %21
        .data   %E2 %70 %00

        ; ( v a -- ) store value v into address a
M_E281: CALL    M_E208          ; popIntoRr4
        LDC     @RR4, R2
        INCW    R4
        LDC     @RR4, R3
        JP      M_E208          ; popIntoRr4

M_E28D: .data   %83
        .data   "VAR"
        .data   M_E27C %0a

        ; call M_E294 will put the next address (after the call M_E294) onto the data stack
M_E294: CALL    M_E099          ; pushRr4
        POP     R2
        POP     R3
        RET

M_E29C: .data   %83
        .data   "CON"             ; constant
        .data   M_E28D %0a
        ; the 2 bytes constant after call M_E2A3 will be stored onto the data stack
M_E2A3: CALL    M_E099          ; pushRr4
        POP     R6
        POP     R7
        LD      R10, #%12
        LDCI    @R10, @RR6
        LDCI    @R10, @RR6
        RET

M_E2B1: .data   %83
        .data   "LIT"             ; literal
        .data   M_E29C %0a
        ; the 2 bytes constant after call M_E2B8 will be stored onto the data stack (like for CON) but proceeded with the next command after the call
M_E2B8: POP     R8
        POP     R9
        LD      R10, #%14
M_E2BE: LDCI    @R10, @RR8
        LDCI    @R10, @RR8
        CALL    M_E099          ; pushRr4
        JP      @RR8

M_E2C7: .data   %84
        .data   "DLIT"
        .data   M_E2B1 %0c
M_E2CF: POP     R8
        POP     R9
        CALL    M_E11A
        LDCI    @R10, @RR8
        LDCI    @R10, @RR8
        LD      R10, #%12
        JR      M_E2BE

M_E2DE: .data   %83
        .data   "ROT"
        .data   M_E2C7 %00
M_E2E5: LD      R5, @R15
        LD      @R15, R3
        INC     R15
        LD      R4, @R15
        LD      @R15, R2
        INC     R15
        LD      R3, @R15
        LD      @R15, R5
        CALL    M_E211              ; prepareForDropCheckEmpty
        LD      R2, @R15
        LD      @R15, R4
        SUB     R15, #3
        RET

M_E2FE: .data   %84
        .data   "SWAP"
        .data   M_E2DE %00
M_E306: LD      R5, @R15
        LD      @R15, R3
        CALL    M_E211              ; prepareForDropCheckEmpty
        LD      R4, @R15
        LD      @R15, R2
        LD      R2, R4
        LD      R3, R5
        DEC     R15
        RET

M_E318: .data   %83
        .data   "DUP"
        .data   M_E2FE %00
M_E31F: CALL    M_E0A7          ; prepareForPushOrError
        LD      @R15, R2
        DEC     R15
        LD      @R15, R3
        RET

M_E329: .data   %84
        .data   "?DUP"          ; duplicate if TOS != 0
        .data   M_E318 %00
M_E331: LD      R4, R2
        OR      R4, R3
        JR      NZ, M_E31F
        RET

M_E338: .data   %83
        .data   "CLS"
        .data   M_E329 %00
M_E33F: JP  %08DD

M_E342: .data   %84
        .data   "OVER"          ; ( x y -- x y x )
        .data   M_E338 %00
M_E34A: LD      R5, @R15
        INC     R15
        LD      R4, @R15
        DEC     R15
        JP      M_E099          ; pushRr4

M_E354: .data   %82
        .data   ">R"            ; ( x -- ) push x to return stack
        .data   M_E342 %00
M_E35A: POP     R4
        POP     R5
        PUSH    R3
        PUSH    R2
        LD      R3, @R15
        INC     R15
        LD      R2, @R15
        CALL    M_E211              ; prepareForDropCheckEmpty
        JP      @RR4

M_E36C: .data   %82
        .data   "R>"            ; ( -- x ) pop from return stack
        .data   M_E354 %00
M_E372: POP     R8
        POP     R9
        CALL    M_E099
        POP     R2
        POP     R3
        JP      @RR8

M_E37F: .data   %81
        .data   "R"             ; ( -- x ) get return stack value
        .data   M_E36C %00
M_E384: CALL    M_E099          ; pushRr4
        POP     R4
        POP     R5
        POP     R2
        POP     R3
        PUSH    R3
        PUSH    R2
        JP      @RR4

M_E395: .data   %81
        .data   "+"
        .data   M_E37F %00
M_E39A: ADD R3, @R15
        INC     R15
        ADC     R2, @R15
        JP      M_E211          ; prepareForDropCheckEmpty

M_E3A2: .data   %81
        .data   "-"
        .data   M_E395 %00
M_E3A7: DEC     %0              ; ? =====================
        CALL    M_E208          ; popIntoRr4
        SUB     R3, R5
        SBC     R2, R4
        RET

M_E3B1: .data   %81
        .data   "*"
        .data   M_E3A2 %00
M_E3B6: CALL    M_E208          ; popIntoRr4
        JP      %00BA

M_E3BC: .data   %81
        .data   "/"
        .data   M_E3B1 %00
M_E3C1: CALL    M_E208          ; popIntoRr4
        JP      %00E0

M_E3C7: .data   %83
        .data   "MOD"
        .data   M_E3BC %00
M_E3CE: CALL    %E208
        JP      %011F

M_E3D4: .data   %82
        .data   "1+"
        .data   M_E3C7 %00
M_E3DA: INCW    R2
        RET

M_E3DD: .data   %86
        .data   "NEGATE"
        .data   M_E3D4 %00
M_E3E7: JP      %E150

M_E3EA: NOP

M_E3EB: .data   %83
        .data   "ABS"
        .data   M_E3DD %00
M_E3F2: OR  R2, R2
        JR  MI, M_E3E7
        RET

M_E3F7: .data   %82
        .data   "U*"
        .data   M_E3EB %00
M_E3FD: CALL    M_E208          ; popIntoRr4
        CALL    M_E028
        JR      M_E425
        DEC     R15
        LD      @R15, R7
        RET

M_E40A: .data   %82
        .data   "M*"
        .data   M_E3F7 %00
M_E410: CALL    M_E00D
        OR      R8, R8
        JR      PL, M_E425
        COM     R2
        COM     R3
        COM     R6
        COM     R7
        INCW    R6
        JR      NZ, M_E425
        INCW    R2
M_E425: DEC     R15
        LD      @R15, R6
        DEC     R15
        LD      @R15, R7
        RET

M_E42E: .data   %82
        .data   "M/"
        .data   M_E40A %00
M_E434: CALL    M_E047
        OR      R8, R8
        JR      PL, M_E425
        COM     R2
        COM     R3
        INCW    R2
        COM     R6
        COM     R7
        INCW    R6
        JR      M_E425

M_E449: .data   %82
        .data   "U/"
        .data   M_E42E %00
M_E44F: CALL    M_E208          ; popIntoRr4
        LD      R10, R4
        LD      R11, R5
        CALL    M_E208          ; popIntoRr4
        CALL    M_E071
        JR      M_E425

M_E45E: .data   %85
        .data   "*/MOD"
        .data   M_E449 %00
M_E467: CALL    M_E2E5
        CALL    M_E2E5
        CALL    M_E410
        CALL    M_E2E5
        JR      M_E434

M_E475: .data   %82
        .data   "*/"
        .data   M_E45E %00
M_E47B: CALL    M_E467
        CALL    M_E306
        JP      M_E208          ; popIntoRr4

M_E484: .data   %85
        .data   "M/MOD"
        .data   M_E475 %00
M_E48D: CALL    M_E208
        LD      R10, R4
        LD      R11, R5
        CALL    M_E208          ; popIntoRr4
        CALL    M_E071
        DEC     R15
        LD      @R15, R6
        DEC     R15
        LD      @R15, R7
        JP      M_E099          ; pushRr4

M_E4A5: .data   %82
        .data   "DO"
        .data   M_E484 %80
M_E4AB: CALL    M_E208          ; popIntoRr4
        POP     R8
        POP     R9
        PUSH    R3
        PUSH    R2
        PUSH    R5
        PUSH    R4
        CALL    M_E208          ; popIntoRr4
        JP      @RR8

M_E4BF: .data   %85
        .data   "+LOOP"
        .data   M_E4A5 %22
M_E4C8: POP     R8
        POP     R9
        POP     R4
        POP     R5
        POP     R6
        POP     R7
        PUSH    R7
        PUSH    R6
        ADD     R5, R3
        ADC     R4, R2
        PUSH    R5
        PUSH    R4
        LD      R3, #%1A
        LDCI    @R3, @RR8
        LDCI    @R3, @RR8
        SUB     R5, R7
        SBC     R4, R6
        XOR     R2, R4
        LD      R7, R4
        OR      R7, R5
        CALL    M_E208          ; popIntoRr4
        OR      R7, R7
        JR      Z, M_E4FD
        RL      R4
        JR      NC, M_E4FD
        JP      @RR10

M_E4FD: POP     R4
        POP     R4
        POP     R4
        POP     R4
        JP      @RR8

M_E507: .data   %84
        .data   "LOOP"
        .data   M_E4BF %22
M_E50F: CALL    M_E099
        LD      R2, #0
        LD      R3, #1
        JR      M_E4C8

M_E518: .data   %81
        .data   "I"
        .data   M_E507 %00
M_E51D: JP      M_E384

M_E520: .data   %85
        .data   "LEAVE"
        .data   M_E518 %00
M_E529: POP     R8
        POP     R9
        POP     R4
        POP     R5
        POP     R6
        POP     R7
        PUSH    R5
        PUSH    R4
        PUSH    R5
        PUSH    R4
        JP      @RR8

M_E53F: .data   %85
        .data   "BEGIN"
        .data   M_E520 %80
M_E548: RET

M_E549: .data   %85
        .data   "UNTIL"
        .data   M_E53F %22
M_E552: CALL    M_E208          ; popIntoRr4
        POP     R8
        POP     R9
        LD      R10, #%16
        LDCI    @R10, @RR8
        LDCI    @R10, @RR8
        OR      R4, R5
        JR      Z, M_E565
        JP      @RR8
M_E565: JP      @RR6

M_E567: .data   %85
        .data   "WHILE"
        .data   M_E549 %42
M_E570: JR      M_E552

M_E572: .data   %86
        .data   "REPEAT"
        .data   M_E567 %32
M_E57C: POP     R8
        POP     R9
        LD      R10, #%16
        LDCI    @R10, @RR8
        LDCI    @R10, @RR8
        JP      @RR6

M_E588: .data   %85
        .data   "AGAIN"
        .data   M_E572 %22
M_E591: JR      M_E57C

M_E593: .data   %83
        .data   "KEY"
        .data   M_E588 %00
M_E59A: CALL    %0C1D
        LD      R4, #0
        LD      R5, %5A
        JP      M_E099          ; pushRr4

M_E5A4: .data   %82
        .data   "CR"
        .data   M_E593 %00
M_E5AA: JP      %0ACE

M_E5AD: .data   %84
        .data   "EMIT"
        .data   M_E5A4 %00
M_E5B5: CALL    M_E208
        LD      %5A, R5
        JP      %0827

M_E5BD: .data   %84
        .data   "KEY?"
        .data   M_E5AD %00
M_E5C5: CLR     %6D
        CALL    %0C56
        LD      R4, #0
        LD      R5, %6D
        JP      M_E099          ; pushRr4

M_E5D1: LD      R5, %6D
        JP      M_E099          ; pushRr4

M_E5D6: .data   %82
        .data   "DP"
        .data   M_E5BD %00
        ; put the (address) %E5DF onto the data stack
M_E5DC: CALL    M_E294
        .data   %F4 %65 %FF     ;???

M_E5E2: .data   %84
        .data   "HERE"
        .data   M_E5D6 %00
M_E5EA: CALL    M_E5DC
        JR      M_E5F4

M_E5EF: .data   %81
        .data   "@"
        .data   M_E5E2 %00
M_E5F4: LDC     R4, @RR2
        INCW    R2
        LDC     R3, @RR2
        LD      R2, R4
        RET

M_E5FD: .data   %82
        .data   "2+"
        .data   M_E5EF %00
M_E603: INCW    R2
M_E605: INCW    R2
        RET

M_E608: .data   %87
        .data   "ENCLOSE"
        .data   M_E5FD %00
M_E613: LD      R0, #0
        LD      R1, #%80
        LD      R4, R2
        LD      R5, R3
        LD      R7, #%20
M_E61D: LDE     R6, @RR4
        OR      R6, R6
        JR      Z, M_E63A
        LD      R1, R5
        CALL    M_E643
        CP      R6, R7
        JR      Z, M_E61D
M_E62C: LDE     R6, @RR4
        OR      R6, R6
        JR      Z, M_E63A
        INC     R0
        CALL    M_E643
        CP      R6, R7
        JR      NZ, M_E62C
M_E63A: DEC     R1
        LD      R3, R5
        LD      R5, R1
        LDE     @RR4, R0
        RET
M_E643: INC     R5
        TCM     R5, #%0D
        JR      NZ, M_E64C
        ADD     R5, #3
M_E64C: RET

M_E64D: .data   %85
        .data   "-FIND"
        .data   M_E608 %00
M_E656: CALL    M_E5EA          ; HERE
M_E659: LDC     R8, @RR2
        INCW    R2
        LDC     R9, @RR2
        LD      R3, R8
        OR      R3, R9
        LD      R2, #0
        JR      NZ, M_E668
        RET
M_E668: LD      R4, #%FD
        LD      R5, R1
        LDE     R6, @RR4
        LDC     R7, @RR8
        AND     R7, #%7F
        CP      R6, R7
        JR      Z, M_E680
M_E677: SCF
        ADC     R9, R7
        ADC     R2, R8
        LD      R3, R9
        JR      M_E659
M_E680: CALL    M_E643
M_E683: INCW    R8
        LDE     R10, @RR4
        LDC     R11, @RR8
        CALL    M_E643
        DEC     R7
        CP      R10, R11
        JR      NZ, M_E677
        OR      R7, R7
        JR      NZ, M_E683
        ADD     R9, #3
        ADC     R8, R7
        LDC     R14, @RR8
        LD      R2, R8
        LD      R3, R9
        INCW    R2
        RET

M_E6A4: .data   %86
        .data   "EXPECT"
        .data   M_E64D %00
M_E6AE: CALL    M_E208          ; popIntoRr4
M_E6B1: CALL    %0824
        LD      R6, #%5A
        LDCI    @RR2, @R6
        CP      %5A, #%0D
        JR      Z, M_E6C1
        DECW    R4
        JR      NZ, M_E6B1
M_E6C1: LD      R6, #0
        DECW    R2
        LDC     @RR2, R6
        JP      M_E208          ; popIntoRr4

M_E6CA: .data   %89
        .data   "INTERPRET"
        .data   M_E6A4 %00
M_E6D7: CALL    M_E97A
M_E6DA: CALL    M_E277          ; TIB
        CALL    M_E5F4
        CALL    M_E14A
        CALL    M_E208          ; popIntoRr4
        OR      R4, R5
        JR      NZ, M_E6ED
        CALL    M_E13D
M_E6ED: CALL    M_E613
        OR      R0, R0
        JR      NZ, M_E71B
        CALL    M_E93A
M_E6F7: CALL    M_E277          ; TIB
        CALL    M_E281
        CALL    M_E9FD
        CALL    M_E5F4
        CALL    M_E208          ; popIntoRr4
        OR      R4, R5
        JR      Z, M_E70C
        RET
        NOP
M_E70C: LD      R6, #%AF
        LDC     @RR12, R6
        CALL    M_E5EA
        CALL    M_E603
        CALL    M_E208          ; popIntoRr4
        JP      @RR4

M_E71B: CALL    M_E656
        CALL    M_E208          ; popIntoRr4
        LD      R6, R4
        OR      R6, R5
        JR      NZ, M_E768
        CALL    M_E807
        CALL    M_E96D
        CALL    M_E5F4
        CALL    M_E208          ; popIntoRr4
        CALL    M_E7F6
        LD      R6, #%E2
        LDC     @RR12, R6
        INCW    R12
        INCW    R4
        JR      Z, M_E74E
        LD      R6, #%CF
        LDC     @RR12, R6
        INCW    R12
        LD      R6, #%12
        LDCI    @RR12, @R6
        LDCI    @RR12, @R6
        JR      M_E754

M_E74E: LD      R6, #%B8
        LDC     @RR12, R6
        INCW    R12
M_E754: CALL    M_E208          ; popIntoRr4
        LD      R6, #%12
        LDCI    @RR12, @R6
        LDCI    @RR12, @R6
        CALL    M_E208          ; popIntoRr4
M_E760: CALL    M_E277          ; TIB
        CALL    M_E5F4
        JR      M_E6ED

M_E768: TM      R14, #1
        JR      Z, M_E77D
        PUSH    R5
        PUSH    R4
        CALL    M_E6F7
        POP     R4
        POP     R5
        CALL    @RR4
        JP      M_E6DA

M_E77D: CALL    M_E7F6
        LD      R6, #%14
        LDCI    @RR12, @R6
        LDCI    @RR12, @R6
        LD      R10, R12
        LD      R11, R13
        LD      R6, R14
        AND     R6, #6
        JR      Z, M_E7C1
        CP      R6, #2
        JR      Z, M_E79F
        CP      R6, #4
        JR      NZ, M_E7A5
        INCW    R12
        INCW    R12
M_E79F: INCW    R12
        INCW    R12
        JR      M_E7C1

M_E7A5: CLR     R7
M_E7A7: INCW    R12
        LDE     R6, @RR2
        INC     R3
        TCM     R3, #%0D
        JR      NZ, M_E7B4
        ADD     R3, #3
M_E7B4: CP      R6, #%22
        JR      Z, M_E7BE
        LDC     @RR12, R6
        INC     R7
        JR      M_E7A7

M_E7BE: LDC     @RR10, R7
        NOP
M_E7C1: TM      R14, #%10
        JR      Z, M_E7D0
        POP     R8
        POP     R9
        LDC     @RR8, R12
        INCW    R8
        LDC     @RR8, R13
M_E7D0: TM      R14, #%20
        JR      Z, M_E7E1
        POP     R8
        POP     R9
        LDC     @RR10, R8
        INCW    R10
        LDC     @RR10, R9
        DECW    R10
M_E7E1: TM      R14, #%40
        JR      Z, M_E7EA
        PUSH    R11
        PUSH    R10
M_E7EA: TM      R14, #%80
        JR      Z, M_E7F3
        PUSH    R13
        PUSH    R12
M_E7F3: JP      M_E6ED

M_E7F6: LD      R6, #%D6
        LDC     @RR12, R6
        INCW    R12
        RET

M_E7FD: .data   %86
        .data   "NUMBER"
        .data   M_E6CA %00
M_E807: CALL    M_E277          ; TIB
        CALL    M_E281
        CALL    M_E099          ; pushRr4
        LD      R0, #0
        LD      R2, #0
        LD      R3, #0
        LD      R4, #0
        LD      R5, #0
        LD      R14, #0
        CALL    M_E099          ; pushRr4
        CALL    M_E099          ; pushRr4
        LD      R2, #%FD
        LD      R3, R1
        LD      R4, #%FF
        LD      R5, #%FF
        CALL    M_E099          ; pushRr4
        CALL    M_E96D
        CALL    M_E281
M_E833: INC     R3
        TCM     R3, #%0D
        JR      NZ, M_E83C
        ADD     R3, #3
M_E83C: LDC     R7, @RR2
        CP      R7, #%20
        JR      Z, M_E8B5
        CP      R7, #%2E
        JR      Z, M_E8A5
        CP      R7, #%2D
        JR      NZ, M_E851
        LD      R14, #%FF
        JR      M_E833

M_E851: OR      R7, R7
        JR      Z, M_E8B5
        OR      R0, R0
        JR      Z, M_E867
        CALL    M_E96D
        CALL    M_E5F4
        INCW    R2
        CALL    M_E96D
        CALL    M_E281
M_E867: SUB     R7, #%30
        JR      C, M_E8BC
        CP      R7, #%0A
        JR      C, M_E879
        SUB     R7, #7
        CP      R7, #%0A
        JR      C, M_E8BC
M_E879: CALL    M_E2E5
        CALL    M_E2E5
        CALL    M_E98B
        CALL    M_E5F4
        CP      R7, R3
        JR      NC, M_E8BC
        PUSH    R7
        CALL    M_E917
        CALL    M_E208          ; popIntoRr4
        LD      R6, #0
        POP     R7
        ADD     R3, R7
        ADC     R2, R6
        ADC     R5, R6
        ADC     R4, R6
        CALL    M_E099          ; pushRr4
        CALL    M_E2E5
M_E8A3: JR      M_E833

M_E8A5: INC     R0
        LD      R4, #0
        LD      R5, #0
        CALL    M_E099          ; pushRr4
        CALL    M_E96C
        CALL    M_E281
        JR      M_E8A3

M_E8B5: CALL    M_E208          ; popIntoRr4
        INC     R14
        JR      Z, M_E8EF
        RET

M_E8BC: LD      R3, R1
        LD      R2, #%FD
        INCW    R2
        NOP
M_E8C3: LD      R6, #%5A
        LDCI    @R6, @RR2
        CP      %5A, #%20
        JR      Z, M_E8D9
        CALL    %0827
        TCM     R3, #%0D
        JR      NZ, M_E8C3
        ADD     R3, #3
        JR      M_E8C3

M_E8D9: LD      %5A, #%3F
        CALL    %0827
        LD      R6, #%30
        JP      M_E0B2

M_E8E4: .data   %87
        .data   "DNEGATE"
        .data   M_E7FD %00
M_E8EF: CALL    M_E208
        COM     R2
        COM     R3
        COM     R4
        COM     R5
        INCW    R2
        JR      NZ, M_E900
M_E8FE: INCW    R4
M_E900: JP      M_E099          ; pushRr4

M_E903: .data   %84
        .data   "DABS"
        .data   M_E8E4 %00
M_E90B: NOP
        OR      R2, R2
        JR      MI, M_E8EF
        RET

M_E911: .data   %82
        .data   "D*"
        .data   M_E903 %00
M_E917: CALL    M_E306
        CALL    M_E34A
        CALL    M_E3FD
        CALL    M_E208          ; popIntoRr4
        CALL    M_E2E5
        CALL    M_E2E5
        CALL    M_E3FD
        CALL    M_E2E5
        JP      M_E39A

M_E932: .data   %84
        .data   "?CSP"
        .data   M_E911 %00
M_E93A: CALL    M_E998
        CALL    M_E9AA
        CALL    M_E5F4
        CALL    M_E208          ; popIntoRr4
        LD      R6, #%4A
        JR      M_E959

M_E94A: .data   %86
        .data   "?PAIRS"
        .data   M_E932 %00
M_E954: CALL    M_E208          ; popIntoRr4
        LD      R6, #%49
M_E959: SUB     R3, R5
        SBC     R2, R4
        OR      R2, R3
        JP      NZ, M_E0B2
        JP      M_E208          ; popIntoRr4

M_E965: .data   %83
        .data   "DPL"
        .data   M_E94A %00
M_E96C: NOP
        ; put the (address) %E670 onto the data stack
M_E96D: CALL    M_E294
        .data   %FFFF

M_E972: .data   %84
        .data   "!CSP"
        .data   M_E965 %00
M_E97A: CALL    M_E998
        CALL    M_E9AA
        JP      M_E281

M_E983: .data   %84
        .data   "BASE"
        .data   M_E972 %00
        ; put the (address) %E98E onto the data stack
M_E98B: CALL    M_E294
        .data   %000a

M_E990: .data   %83
        .data   "RP@"
        .data   M_E983 %00
M_E997: NOP
M_E998: LD      R5, SPL
        LD      R4, SPH
        INCW    R4
        INCW    R4
        JP      M_E099          ; pushRr4

M_E9A3: .data   %83
        .data   "CSP"
        .data   M_E990 %00
        ; put the (address) %E9AD onto the data stack
M_E9AA: CALL    M_E294
        .data   %FDFC

M_E9AF: .data   %85
        .data   "WORDS"
        .data   M_E9A3 %00
M_E9B8: CALL    M_E5DC
        CALL    M_E5F4
M_E9BE: LD      R5, #7
M_E9C0: LDC     R8, @RR2
        INCW    R2
        LDC     R9, @RR2
        LD      R4, R8
        OR      R4, R9
        JP      Z, M_E208           ; popIntoRr4
        LDC     R7, @RR8
        INCW    R8
        AND     R7, #%7F
M_E9D4: LD      R6, #%5A
        LDCI    @R6, @RR8
        CALL    %0827
        DJNZ    R7, M_E9D4
        LD      %5A, #%0D
        CALL    %0827
        LD      R2, R8
        LD      R3, R9
        DJNZ    R5, M_E9C0
        CALL    %0824
        CP      %5A, #%0D
        JR      Z, M_E9BE
        JP      M_E208          ; popIntoRr4

M_E9F4: .data   %85
        .data   "STATE"
        .data   M_E9AF %00
        ; put the (address) %EA00 onto the data stack
M_E9FD: CALL    M_E294
        .data   %0000

M_EA02: .data   %86
        .data   "CREAT@"
        .data   M_E9F4 %00
M_EA0C: CALL    M_E5EA
        CALL    M_E31F
        CALL    M_E9FD
        CALL    M_E281
        LDC     R10, @RR2
        INCW    R2
        LDC     R11, @RR2
        DECW    R2
        LD      R12, R2
        LD      R13, R3
        CALL    M_E208          ; popIntoRr4
        CALL    M_E277          ; TIB
        CALL    M_E5F4
        CALL    M_E613
        LD      R6, #%44
        OR      R0, R0
        JR      Z, M_EA6A
        LD      R6, R0
        OR      R6, #%80
        LDC     @RR12, R6
        INCW    R12
M_EA3F: CALL    M_E643
        LDE     R6, @RR4
        LDC     @RR12, R6
        INCW    R12
        DJNZ    R0, M_EA3F
        LD      R4, R12
        LD      R5, R13
        CALL    M_E099          ; pushRr4
        CALL    M_E5DC
        CALL    M_E281
        LD      R6, #%1A
        LDCI    @RR12, @R6
        LDCI    @RR12, @R6
        CLR     R6
        LDC     @RR12, R6
        INCW    R12
        CALL    M_E277          ; TIB
        CALL    M_E281
        RET
M_EA6A: PUSH    R6
        CALL    M_E5EA
        CALL    M_E5F4
        CALL    M_E9FD
        CALL    M_E5F4
        CALL    M_E31F
        CALL    M_E5DC
        CALL    M_E281
        CALL    M_E281
        LD      R4, #0
        LD      R5, #0
        CALL    M_E099          ; pushRr4
        CALL    M_E9FD
        CALL    M_E281
        POP     R6
        JP      M_E0B2

M_EA96: .data   %88
        .data   "VARIABLE"
        .data   M_EA02 %01
M_EAA2: CALL    M_EA0C
        LD      R8, #%E2
        LD      R9, #%94
        CALL    M_EADA
        INCW    R12
        INCW    R12
M_EAB0: LD      R4, R12
        LD      R5, R13
        INCW    R12
        INCW    R12
        CALL    M_E099          ; pushRr4
        CALL    M_E9FD
        CALL    M_E5F4
        CALL    M_E34A
        CALL    M_E281
        CALL    M_E5DC
        CALL    M_E281
        LD      R4, #0
        LD      R5, #0
        CALL    M_E099          ; pushRr4
        CALL    M_E9FD
        JP      M_E281

M_EADA: CALL    M_E7F6
        LD      R6, #%18
        LDCI    @RR12, @R6
        LDCI    @RR12, @R6
        RET

M_EAE4: .data   %88
        .data   "CONSTANT"
        .data   M_EA96 %01
M_EAF0: CALL    M_EA0C
        LD      R8, #%E2
        LD      R9, #%A3
        CALL    M_EADA
        LD      R6, #%12
        LDCI    @RR12, @R6
        LDCI    @RR12, @R6
        CALL    M_E208          ; popIntoRr4
        JR      M_EAB0

M_EB05: .data   %81
        .data   ";"
        .data   M_EAE4 %01
M_EB0A: CALL    M_E998
        CALL    M_E9AA
        CALL    M_E5F4
        CALL    M_E208          ; popIntoRr4
        SUB     R3, R5
        SBC     R2, R4
        OR      R2, R3
        LD      R6, #%49
        JP      NZ, M_EA6A
        CALL    M_E16B
        LD      R6, #%AF
        LDC     @RR12, R6
        INCW    R12
        JP      M_EAB0

M_EB2D: .data   %81
        .data   ":"
        .data   M_EB05 %01
M_EB32: CALL    M_EA0C
        POP     R4
        POP     R5
        CALL    M_E97A
        JP      M_E760

M_EB3F: NOP
        NOP
M_EB41: NOP
        NOP
        NOP
        NOP

M_EB45: .data   %86
        .data   "FORGET"
        .data   M_EB2D %01
M_EB4F: CALL    M_E277
        CALL    M_E5F4
        CALL    M_E613
        LD      R6, #%44
        OR      R0, R0
        JP      Z, M_E0B2
        CALL    M_E656
        LD      R6, R2
        OR      R6, R3
        JP      Z, M_E8BC
        CALL    M_EB8E
        CALL    M_E31F
        CALL    M_EBB3
        CALL    M_E5F4
        CALL    M_E34A
        CALL    M_E281
M_EB7B: CALL    M_E5DC
        CALL    M_E281
        CALL    M_E277          ; TIB
        JP      M_E281

M_EB87: .data   %83
        .data   "NFA"
        .data   M_EB45 %00
M_EB8E: SUB     R3, #4
        LD      R4, #0
        SBC     R2, R4
M_EB95: LDC     R5, @RR2
        OR      R5, R5
        JR      MI, M_EBA0
        INC     R4
        DECW    R2
        JR      M_EB95

M_EBA0: AND     R5, #%7F
        CP      R4, R5
        JR      Z, M_EBAB
        CLR     R2
        CLR     R3
M_EBAB: RET

M_EBAC: .data   %83
        .data   "LFA"
        .data   M_EB87 %00
M_EBB3: LDC     R5, @RR2
        AND     R5, #%7F
        SCF
        ADC     R3, R5
        ADC     R2, #0
        RET

M_EBBF: .data   %82
        .data   ".!"
        .data   M_EBAC %00
M_EBC5: CALL    M_E208          ; popIntoRr4
        LDC     R6, @RR4
        INCW    R4
        LDC     R7, @RR4
        ADD     R7, R3
        ADC     R6, R2
        LDC     @RR4, R7
        DECW    R4
        LDC     @RR4, R6
        JP      M_E208          ; popIntoRr4

M_EBDB: .data   %84
        .data   "CODE"
        .data   M_EBBF %01
M_EBE3: CALL    M_EA0C
        LD      %6E, R12
        LD      %6F, R13
        CALL    %1352
        SRP     #%10
        LD      R12, %70
        LD      R13, %71
        CALL    M_E277          ; TIB
        CALL    M_E5F4
        LD      R4, #0
        LDE     @RR2, R4
        CALL    M_E208          ; popIntoRr4
        JP      M_EAB0

M_EC03: .data   %83
        .data   "CFA"
        .data   M_EBDB %00
M_EC0A: LDC     R5, @RR2
        AND     R5, #%7F
        ADD     R5, #3
        ADD     R3, R5
        ADC     R2, #0
        RET

M_EC18: .data   %83
        .data   "ID."
        .data   M_EC03 %00
M_EC1F: LDC     R5, @RR2
        AND     R5, #%7F
        INCW    R2
M_EC26: LD      R4, #%5A
        LDCI    @R4, @RR2
        CALL    %0827
        DJNZ    R5, M_EC26
        CALL    M_EC3E
        JP      M_E208          ; popIntoRr4

M_EC35: .data   %85
        .data   "SPACE"
        .data   M_EC18 %00
M_EC3E: LD      %5A, #%20
        JP      %0827

M_EC44: .data   %86
        .data   "SPACES"
        .data   M_EC35 %00
M_EC4E: CALL    M_E208
M_EC51: CALL    M_EC3E
        DJNZ    R5, M_EC51
        RET

M_EC57: .data   %F9AF           ; ???

M_EC59: .data   %82
        .data   "#>"
        .data   M_EC44 %00
M_EC5F: CALL    M_E208
        CALL    M_E208
        CALL    M_ECED
        CALL    M_E5F4
        LD      R5, R3
        LD      R4, #0
        COM     R5
        INC     R5
        JP      M_E099          ; pushRr4

M_EC75: .data   %84
        .data   "TYPE"
        .data   M_EC59 %00
M_EC7D: CALL    M_E208
M_EC80: LD      R6, #%5A
        LDCI    @R6, @RR2
        CALL    %0827
        DJNZ    R5, M_EC80
        JP      M_E208

M_EC8C: .data   %84
        .data   "HOLD"
        .data   M_EC75 %00
M_EC94: CALL    M_ECED
        CALL    M_E5F4
        CALL    M_E208          ; popIntoRr4
        DECW    R4
        LDC     @RR4, R3
        CALL    M_E110
        CALL    M_ECED
        JP      M_E281

M_ECAA: .data   %84
        .data   "SIGN"
        .data   M_EC8C %00
M_ECB2: CALL    M_E2E5
        OR      R2, R2
        JP      PL, M_E208          ; popIntoRr4
        LD      R3, #%2D
        JR      M_EC94

M_ECBE: .data   %84
        .data   "S->D"
        .data   M_ECAA %00
M_ECC6: LD      R4, #%FF
        LD      R5, #%FF
        OR      R2, R2
        JR      MI, M_ECD0
        INCW    R4
M_ECD0: JP      M_E099          ; pushRr4

M_ECD3: .data   %82
        .data   "<#"
        .data   M_ECBE %00
M_ECD9: LD      R4, #%E2
        LD      R5, #0
        CALL    M_E099          ; pushRr4
        CALL    M_ECED
        JP      M_E281

M_ECE6: .data   %83
        .data   "HLD"
        .data   M_ECD3 %00
M_ECED: CALL    M_E294
        .data   %E1FE

M_ECF2: .data   %81
        .data   "#"
        .data   M_ECE6 %00
M_ECF7: CALL    M_E98B
        CALL    M_E5F4
        CALL    M_E48D
        CALL    M_E2E5
        CALL    M_E208          ; popIntoRr4
        LD      R7, R5
        CP      R7, #%0A
        JR      C, M_ED10
        ADD     R7, #7
M_ED10: ADD     R7, #%30
        LD      R5, R7
        LD      R4, #0
        CALL    M_E099          ; pushRr4
        JP      M_EC94

M_ED1D: .data   %82
        .data   "#S"
        .data   M_ECF2 %00
M_ED23: CALL    M_ECF7
        CALL    M_E208          ; popIntoRr4
        LD      R7, R2
        OR      R7, R3
        CALL    M_E099          ; pushRr4
        OR      R7, R2
        OR      R7, R3
        JR      NZ, M_ED23
        RET

M_ED37: .data   %82
        .data   "U."
        .data   M_ED1D %00
M_ED3D: CALL    M_E2B8          ; push 0
        .data   %0000
        CALL    M_ECD9
        CALL    M_ED23
        CALL    M_EC5F
        JP      M_E189

        RET

M_ED4F: .data   %81
        .data   "."
        .data   M_ED37 %00
M_ED54: CALL    M_E31F
        CALL    M_ECC6
M_ED5A: CALL    M_E90B
        CALL    M_ECD9
        CALL    M_ED23
        CALL    M_ECB2
        CALL    M_EC5F
        CALL    M_E189
        RET

M_ED6D: .data   %82
        .data   "D."
        .data   M_ED4F %00
M_ED73: CALL    M_E306
        CALL    M_E34A
        JR      M_ED5A

M_ED7B: RET
        .data   %85

M_ED7D: .data   %84
        .data   "VIEW"
        .data   M_ED6D %01
M_ED85: CALL    M_E277
        CALL    M_E5F4
        CALL    M_E613
        LD      R6, #%44
        OR      R0, R0
        JP      Z, M_E0B2
        CALL    M_E656
        LD      R6, R2
        OR      R6, R3
        JP      Z, M_E8BC
        LD      R12, R2
        LD      R13, R3
        CALL    M_E208          ; popIntoRr4
        CALL    M_E277          ; TIB
        CALL    M_E281
M_EDAC: LDC     R4, @RR12
        CP      R4, #%D6
        JR      Z, M_EDD7
        CP      R4, #%8D
        JR      Z, M_EDD7
        CP      R4, #%AF
        JR      Z, M_EE05
M_EDBD: LD      %6E, R12
        LD      %6F, R13
        CALL    M_E221 ; printString
        .data   %05
        .data   "CODE "
        CALL    %0824
        CP      %5A, #%0D
        JR      NZ, M_EE05
        CALL    %1352
        JR      M_EE05

M_EDD7: LD      R0, R4
        INCW    R12
        LD      R6, #%14
        LDCI    @R6, @RR12
        LDCI    @R6, @RR12
        DECW    R4
        LDC     R14, @RR4
        INCW    R4
        CALL    M_E17D
        CALL    M_EB8E
        OR      R2, R2
        JR      NZ, M_EDF8
        SUB     R13, #3
        SBC     R12, R2
        JR      M_EDBD

M_EDF8: CALL    M_EC1F
        AND     R14, #%0E
        JR      NZ, M_EE08
M_EE00: CP      R0, #%8D
        JR      NZ, M_EDAC
M_EE05: JP      M_E0FD

M_EE08: TM      R14, #8
        JR      NZ, M_EE14
        ADD     R13, R14
        ADC     R12, #0
        JR      M_EE00

M_EE14: CP      R14, #%0A
        JR      NZ, M_EE27
        LD      R6, #%14
        LDCI    @R6, @RR12
        LDCI    @R6, @RR12
        CALL    M_E099          ; pushRr4
        CALL    M_ED54
        JR      M_EE00

M_EE27: CP      R14, #%0C
        JR      NZ, M_EE43
        CALL    M_E099          ; pushRr4
        LD      R6, #%14
        LDCI    @R6, @RR12
        LDCI    @R6, @RR12
        LD      R6, #%12
        LDCI    @R6, @RR12
        LDCI    @R6, @RR12
        CALL    M_E099          ; pushRr4
        CALL    M_ED73
        JR      M_EE00

M_EE43: LDC     R8, @RR12
        INCW    R12
M_EE47: LD      R7, #%5A
        LDCI    @R7, @RR12
        CALL    %0827
        DJNZ    R8, M_EE47
        CALL    M_E18F
        JR      M_EE00

M_EE55: .data   %83
        .data   "SP@"
        .data   M_ED7D %00
M_EE5C: LD      R5, R15
        LD      R4, #0
        JP      M_E099          ; pushRr4

M_EE63: .data   %83
        .data   "SP!"
        .data   M_EE55 %00
M_EE6A: LD      R15, #%50
        RET

M_EE6D: .data   %83
        .data   "RP!"
        .data   M_EE63 %00
M_EE74: POP     R4
        POP     R5
        CLR     SPL
        LD      SPH, #%FE
        JP      @RR4

M_EE7F: .data   %83
        .data   "AND"
        .data   M_EE6D %00
M_EE86: CALL    M_E208
        AND     R2, R4
        AND     R3, R5
        RET

M_EE8E: .data   %82
        .data   "OR"
        .data   M_EE7F %00
M_EE94: CALL    %E208
        OR      R2, R4
        OR      R3, R5
        RET

M_EE9C: .data   %83
        .data   "XOR"
        .data   M_EE8E %00
M_EEA3: CALL    %E208
        XOR     R2, R4
        XOR     R3, R5
        RET

M_EEAB: .data   %81
        .data   "="
        .data   M_EE9C %00
M_EEB0: CALL    %E208
        SUB     R3, R5
        SBC     R2, R4
M_EEB7: OR      R2, R3
        CLR     R2
        CLR     R3
        JR      NZ, M_EEC1
        DECW    R2
M_EEC1: RET

M_EEC2: .data   %81
        .data   ">"
        .data   M_EEAB %00
M_EEC7: CALL    M_E208          ; popIntoRr4
M_EECA: SUB     R5, R3
        SBC     R4, R2
        CLR     R2
        CLR     R3
        JR      GE, M_EEC1
        DECW    R2
        RET

M_EED7: .data   %81
        .data   "<"
        .data   M_EEC2 %00
M_EEDC: CALL    M_E208          ; popIntoRr4
M_EEDF: SUB     R3, R5
        SBC     R2, R4
        CLR     R2
        CLR     R3
        JR      GE, M_EEC1
        DECW    R2
        RET

M_EEEC: .data   %82
        .data   "0="
        .data   M_EED7 %00
M_EEF2: JR      M_EEB7

M_EEF4: .data   %82
        .data   "0<"
        .data   M_EEEC %00
M_EEFA: CLR     R4
        CLR     R5
        JR      M_EEDF

M_EF00: .data   %82
        .data   "0>"
        .data   M_EEF4 %00
M_EF06: CLR     R4
        CLR     R5
        JR      M_EECA

M_EF0C: .data   %82
        .data   "IF"
        .data   M_EF00 %42
M_EF12: CALL    M_E208
        POP     R6
        POP     R7
        LD      R10, #%18
        LDCI    @R10, @RR6
        LDCI    @R10, @RR6
        OR      R4, R5
        JR      NZ, M_EF25
        JP      @RR8
M_EF25: JP      @RR6

M_EF27: .data   %84
        .data   "ELSE"
        .data   M_EF0C %52
M_EF2F: POP     R6
        POP     R7
        LD      R10, #%18
        LDCI    @R10, @RR6
        LDCI    @R10, @RR6
        JP      @RR8

M_EF3B: .data   %84
        .data   "THEN"
        .data   M_EF27 %10
M_EF43: RET

M_EF44: .data   %84
        .data   "/MOD"
        .data   M_EF3B %00
M_EF4C: CALL    M_E208          ; popIntoRr4
        CALL    %00E0
        LD      R8, R2
        CALL    M_E099          ; pushRr4
        CALL    %0124
        JP      M_E306

M_EF5D: .data   %83
        .data   "MIN"
        .data   M_EF44 %00
M_EF64: CALL    M_E120
        JR      MI, M_EF6D
M_EF69: LD      R2, R4
        LD      R3, R5
M_EF6D: RET

M_EF6E: .data   %83
        .data   "MAX"
        .data   M_EF5D %00
M_EF75: CALL    M_E120
        JR      MI, M_EF69
        RET

M_EF7B: .data   %82
        .data   "D+"
        .data   M_EF6E %00
M_EF81: CALL    %E12C
        ADD     R3, R9
        ADC     R2, R8
        ADC     R5, R11
        ADC     R4, R10
        JP      M_E099

M_EF8F: .data   %82
        .data   "+-"
        .data   M_EF7B %00
M_EF95: CALL    M_E208
        XOR     R4, R2
        JP      MI, M_E3E7
        RET

M_EF9E: .data   %83
        .data   "D+-"
        .data   M_EF8F %00
M_EFA5: CALL    M_E208
        XOR     R4, R2
        JP      MI, M_E8EF
        RET

M_EFAE: .data   %87
        .data   "DECIMAL"
        .data   M_EF9E %00
M_EFB9: CALL    M_E2B8
        .data   %000a
        CALL    M_E98B
        JP      M_E281

M_EFC4: .data   %83
        .data   "HEX"
        .data   M_EFAE %00
M_EFCB: CALL    M_E2B8
        .data   %0010
        CALL    M_E98B
        JP      M_E281

M_EFD6: .data   %84
        .data   "2DUP"
        .data   M_EFC4 %00
M_EFDE: CALL    M_E34A
        JP      M_E34A

M_EFE4: .data   %82
        .data   "C@"
        .data   M_EFD6 %00
M_EFEA: LDC     R3, @RR2
        CLR     R2
        RET

M_EFEF: .data   %82
        .data   "C!"
        .data   M_EFE4 %00
M_EFF5: CALL    M_E208
        LDC     @RR4, R3
        JP      M_E208

M_EFFD: .data   %81
        .data   "?"
        .data   M_EFEF %00
M_F002: CALL    M_E5F4
        CALL    M_ED54
        RET

M_F009: .data   %86
        .data   "TOGGLE"
        .data   M_EFFD %00
M_F013: CALL    M_E208
        LDE     R4, @RR2
        XOR     R4, R5
        LDE     @RR2, R4
        JP      M_E208

M_F01F: .data   %85
        .data   "CMOVE"
        .data   M_F009 %00
M_F028: LD      R8, R2
        LD      R9, R3
        CALL    M_E208          ; popIntoRr4
        CALL    M_E208          ; popIntoRr4
M_F032: LDE     R7, @RR2
        LDE     @RR4, R7
        INCW    R2
        INCW    R4
        DECW    R8
        JR      NZ, M_F032
        JP      M_E208          ; popIntoRr4

M_F041: .data   %84
        .data   "FILL"
        .data   M_F01F %00
M_F049: LD      R7, R3
        CALL    M_E208
        CALL    %E208
M_F051: LDE     @RR2, R7
        INCW    R2
        DECW    R4
        JR      NZ, M_F051
        JP      M_E208

M_F05C: .data   %85
        .data   "ERASE"
        .data   M_F041 %00
M_F065: CALL    M_E2B8
        .data   %0000
        JP      M_F049

M_F06D: .data   %86
        .data   "BLANKS"
        .data   M_F05C %00
M_F077: CALL    M_E2B8
        .data   %0020
        JP      M_F049

M_F07F: .data   %85
        .data   "COUNT"
        .data   M_F06D %00
M_F088: LDE     R5, @RR2
        CLR     R4
        INCW    R2
        JP      M_E099

M_F091: .data   %82
        .data   ".R"
        .data   M_F07F %00
M_F097: CALL    M_E306
        CALL    M_E31F
        CALL    M_ECC6
M_F0A0: CALL    M_E90B
        CALL    M_ECD9
        CALL    M_ED23
        CALL    M_ECB2
M_F0AC: CALL    M_EC5F
        CALL    M_E2E5
        CALL    M_E34A
        CALL    M_E3A7
        CALL    M_EC4E
        JP      M_EC7D

M_F0BE: .data   %83
        .data   "D.R"
        .data   M_F091 %00
M_F0C5: CALL    M_E2E5
        CALL    M_E2E5
        CALL    M_E306
        CALL    M_E34A
        JR      M_F0A0

M_F0D3: .data   %83
        .data   "U.R"
        .data   M_F0BE %00
M_F0DA: CALL    M_E306
        CALL    M_E2B8          ; literal
        .data   %0000
        CALL    M_ECD9
        CALL    M_ED23
        JR      M_F0AC

M_F0EA: .data   %84
        .data   "QUIT"
        .data   M_F0D3 %00
M_F0F2: CALL    M_E2B8
        .data   %0000
        CALL    M_E9FD
        CALL    M_E281
        JP      M_E0DD

M_F100: .data   %85
        .data   "BUSIN"
        .data   M_F0EA %00
M_F109: LD      R3, @R3
        CLR     R2
        RET

M_F10E: .data   %86
        .data   "BUSOUT"
        .data   M_F100 %00
M_F118: CALL    M_E208          ; popIntoRr4
        LD      @R5, R3
        JP      M_E208          ; popIntoRr4

M_F120: .data   %85
        .data   "ALLOT"
        .data   M_F10E %00
M_F129: CALL    M_E5EA
        CALL    M_E39A
        JR      M_F139

M_F131: .data   %84
        .data   "LINK"
        .data   M_F120 %00
M_F139: CALL    M_E31F
        CALL    M_E5EA
        CALL    M_E5F4
        CALL    M_E306
        CALL    M_E281
        CALL    M_E5DC
        JP      M_E281

M_F14E: .data   %85
        .data   "DEPTH"
        .data   M_F131 %00
M_F157: LD      R5, #%50
        SUB     R5, R15
        RR      R5
        LD      R4, #0
        JP      M_E099          ; pushRr4

M_F162: .data   %84
        .data   "ROLL"
        .data   M_F14E %00
M_F16A: LD      R4, R3
        ADD     R4, R4
        ADD     R4, R15
        LD      R5, R4
        LD      R3, @R4
        DEC     R5
        INC     R4
        LD      R6, #%31
        CP      R4, #%51
        JP      NC, M_E0B2
        LD      R2, @R4
M_F181: LD      R7, @R5
        LD      @R4, R7
        DEC     R4
        DEC     R5
        LD      R7, @R5
        LD      @R4, R7
        DEC     R4
        DEC     R5
        CP      R15, R5
        JR      C, M_F181
        INC     R15
        INC     R15
        RET

M_F198: .data   %84
        .data   "PICK"
        .data   M_F162 %00
M_F1A0: LD      R4, R3
        ADD     R4, R4
        ADD     R4, R15
        LD      R6, #%31
        CP      R4, #%50
        JP      NC, %E0B2
        LD      R3, @R4
        INC     R4
        LD      R2, @R4
        RET

M_F1B4: .data   %84
        .data   "2ROT"
        .data   M_F198 %00
M_F1BC: CALL    M_E2B8          ; literal
        .data   %0005
        CALL    M_F16A
        CALL    M_E2B8          ; literal
        .data   %0005
        JP      M_F16A

M_F1CC: .data   %85
        .data   "2DROP"
        .data   M_F1B4 %00
M_F1D5: CALL    M_E208
        JP      M_E208

M_F1DB: .data   %85
        .data   "2SWAP"
        .data   M_F1CC %00
M_F1E4: CALL    M_E2B8          ; literal
        .data   %0003
        CALL    M_F16A
        CALL    M_E2B8          ; literal
        .data   %0003
        JP      M_F16A

M_F1F4: .data   %82
        .data   "D-"
        .data   M_F1DB %00
M_F1FA: CALL    M_E157
        JP      M_E099

M_F200: .data   %83
        .data   "DU<"
        .data   M_F1F4 %00
M_F207: CALL    M_E163
        JR      NC, M_F20E
M_F20C: DECW    R2
M_F20E: RET

M_F20F: .data   %82
        .data   "D<"
        .data   M_F200 %00
M_F215: CALL    M_E163
        JR      LT, M_F20C
        RET

M_F21B: .data   %82
        .data   "D="
        .data   M_F20F %00
M_F221: CALL    M_E157
M_F224: OR      R2, R3
        OR      R2, R4
        OR      R2, R5
        CLR     R2
        CLR     R3
        JR      Z, M_F20C
        RET

M_F231: .data   %83
        .data   "D0="
        .data   M_F21B %00
M_F238: CALL    M_E208          ; popIntoRr4
        JR      M_F224

M_F23D: .data   %85
        .data   "2OVER"
        .data   M_F231 %00
M_F246: CALL    M_E2B8          ; literal
        .data   %0003
        CALL    M_F1A0
        CALL    M_E2B8          ; literal
        .data   %0003
        JP      M_F1A0

M_F256: .data   %84
        .data   "DMAX"
        .data   M_F23D %00
M_F25E: CALL    M_F246
        CALL    M_F246
        CALL    M_F215
        CALL    M_EF12
        .data   M_F272
        CALL    M_F1E4
        CALL    M_EF43
M_F272: JP      M_F1D5

M_F275: .data   %84
        .data   "DMIN"
        .data   M_F256 %00
M_F27D: CALL    M_F246
        CALL    M_F246
        CALL    M_F215
        CALL    M_EF12
        .data   M_F290
        CALL    M_EF2F
        .data   M_F296
M_F290: CALL    M_F1E4
        CALL    M_EF43
M_F296: JP      M_F1D5

M_F299: .data   %82
        .data   "U<"
        .data   M_F275 %00
M_F29F: CALL    M_E120
        CLR     R2
        CLR     R3
        JR      C, M_F30C       ; ???????
        RET

M_F2A9: .data   %82
        .data   "1-"
        .data   M_F299 %00
M_F2AF: JR      M_F2B9

M_F2B1: .data   %82
        .data   "2-"
        .data   M_F2A9 %00
M_F2B7: DECW    R2
M_F2B9: DECW    R2
        RET

M_F2BC: .data   %82
        .data   "2/"
        .data   M_F2B1 %00
M_F2C2: SRA     R2
        RRC     R3
        RET

M_F2C7: .data   %83
        .data   "D2/"
        .data   M_F2BC %00
M_F2CE: CALL    M_E208          ; popIntoRr4
        SRA     R4
        RRC     R5
        RRC     R2
        RRC     R3
        JP      M_E099          ; pushRr4

M_F2DC: .data   %83
        .data   "NOT"
        .data   M_F2C7 %00
M_F2E3: COM     R2
        COM     R3
        RET

M_F2E8: .data   %82
        .data   "+!"
        .data   M_F2DC %00
M_F2EE: CALL    M_E208          ; popIntoRr4
        LDE     R6, @RR4
        INCW    R4
        LDE     R7, @RR4
        ADD     R7, R3
        ADC     R6, R2
        LDE     @RR4, R7
        DECW    R4
        LDE     @RR4, R6
        JP      M_E208          ; popIntoRr4

M_F304: .data   %82
        .data   "2@"
        .data   M_F2E8 %00
M_F30A: CALL    M_E31F
M_F30C: CALL    M_E603          ; incorrect label - see usage of M_F30C
        CALL    M_E5F4
        CALL    M_E306
        JP      M_E5F4

M_F319: .data   %82
        .data   "2!"
        .data   M_F304 %00
M_F31F: CALL    M_E31F
        CALL    M_E35A
        CALL    M_E281
        CALL    M_E372
        CALL    M_E603
        JP      M_E281

M_F331: .data   %86
        .data   "CMOVE>"
        .data   M_F319 %00
M_F33B: LD      R8, R2
        LD      R9, R3
        CALL    M_E208          ; popIntoRr4
        CALL    M_E208          ; popIntoRr4
        ADD     R3, R9
        ADC     R2, R8
        ADD     R5, R9
        ADC     R4, R8
M_F34D: LDE     R7, @RR2
        LDE     @RR4, R7
        DECW    R2
        DECW    R4
        DECW    R8
        JR      NZ, M_F34D
        JP      M_E208          ; popIntoRr4

M_F35C: .data   %88
        .data   "FORTH-83"
        .data   M_F331 %00
M_F368: CALL    M_E221
        .data   %33
        .data   "SUBSET ERWEI-TERT FUER    DOPPELTE     GENAUIGKEIT "
        RET

M_F3A0: .data   %83
        .data   "UM*"
        .data   M_F35C %00
M_F3A7: JP      M_E3FD

M_F3AA: .data   %86
        .data   "UM/MOD"
        .data   M_F3A0 %00
M_F3B4: CALL    M_E48D
        JP      M_E208

M_F3BA: .data   %82
        .data   "R@"
        .data   M_F3AA %00
M_F3C0: JP      M_E384

M_F3C3: .data   %84
        .data   "EXIT"
        .data   M_F3BA %00
M_F3CB: POP     R4
        POP     R5
        RET

M_F3D0: .data   %85
        .data   "INPUT"
        .data   M_F3C3 %00
M_F3D9: CALL    M_E23D
        CALL    M_E277
        CALL    M_E5F4
        CALL    M_E613
        CALL    M_E807
        RET

M_F3E9: .data   %81
        .data   "J"
        .data   M_F3D0 %00
M_F3EE: LD      R6, SPH
        LD      R7, SPL
        ADD     R7, #8
        LDE     R4, @RR6
        INCW    R6
        LDE     R5, @RR6
        JP      M_E099          ; pushRr4

M_F3FE: .data   %82
        .data   "S."
        .data   M_F3E9 %00
M_F404: CALL    M_E2B8
        .data   %004E
        CALL    M_EE5C
        CALL    M_E4AB
M_F40F: CALL    M_E51D
        CALL    M_F109
        CALL    M_E51D
        CALL    M_E3DA
        CALL    M_F109
        CALL    M_E2B8          ; push 0
        .data   %0100
        CALL    M_F3A7
        CALL    M_E208          ; popIntoRr4
        CALL    M_E39A
        CALL    M_E5AA
        CALL    M_ED54
        CALL    M_E2B8          ; push 2
        .data   %0002
M_F437: CALL    M_E4C8
        .data   M_F40F
        RET

M_F43D: .data   %85
        .data   "STACK"
        .data   M_F3FE %00
M_F446: CALL    M_F157
        CALL    M_EF06
        CALL    M_EF12
        .data   M_F459
        CALL    M_F404
        CALL    M_EF2F
        .data   M_F464
M_F459: CALL    M_E221
        .data   %04
        .data   "LEER"
        CALL    M_EF43
M_F464: RET

M_F465: .data   M_F43D

M_F467: CALL    M_E9B8
        RET
        .data   M_F43D
        .data   %00
M_F46E: CALL    M_E548
M_F471: CALL    M_E59A
        CALL    M_E31F
        CALL    M_E2B8          ; push %0d
        .data   %000d
        CALL    M_E3A7
        CALL    M_E570
        .data   M_F48F
        CALL    M_ED54
        CALL    M_E5AA
        CALL    M_E57C
        .data   M_F471
M_F48F: CALL    M_E208
        CALL    M_E221
        .data   %05
        .data   "ENTER"
        RET

        .data   %00
        .data   %00
        .data   %00
        .data   %00
