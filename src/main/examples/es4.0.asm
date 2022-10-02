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

        .const COLUMNS = 40
        .const ROWS = 24

        .ORG    %0800

        JP      %F500
        JP      %F503
        JP      %F506
        JP      %F509
        JP      @%56
        NOP
        JP      %F50F

        ; boot
M_0812: JP      M_0870

        ; the next jumps are used from the Tiny-Basic
        ; G(e)TC(har)
M_0815: JP      %FFE6

        ; P(u)TC(har)
M_0818: JP      %FFE9

M_081B: JP      M_1B0A

M_081E: JP      M_1B94

        ; save?
M_0821: JP      M_1C86

M_0824: JP      M_177A

        JP      M_08E0

M_082A: JP      M_0BF8

        ; print
M_082D: JP      M_163A   ; _print

        JP      M_1F00

        JP      M_1F6D

        JP      M_1CAE

        NOP

        ; in: %4E
        ;     %4F
        ;     %51
        ;     %53
        ; destroys: %60-6A
M_083A: PUSH    RP
        SRP     #%60
        CALL    M_0FBB
        LD      R10, %53
        LD      R6, #%FF
        XOR     R6, R3
        LD      R2, #0
        LD      R4, #%60
        LD      R5, #0b1110_1111
        LD      P01M, #%B2
M_0850: TM      %3, #4      ; while P32(BUSY)==0 {}
        .JP     NZ, M_0850  ; -"-
M_0855: LDE     @RR4, R5
        LD      R8, #%31
        RRC     R10
        RLC     R8
        LDE     R9, @RR0
        AND     R9, R6
        OR      R9, @R8    ; read R2(%62) or R3(%63), depending on Bit0(R10)
        LDE     @RR0, R9
        RL      R5
        .JP     C, M_0855
        LD      P01M, #%92
        POP     RP
        RET

        NOP

        ; _boot
M_0870: SRP     #%F0
        LD      R14, #%F7           ; set Stack pointer to f700
        LD      R15, #0
        LD      R8, #%92            ; P01M: P04-P07 = A12-A15
                                    ;       normal external memory timing
                                    ;       P10-P17 = AD0-AD7
                                    ;       external Stack
                                    ;       P00-P03 = A8-A11
        SRP     #%50
        LD      R4, #0
        LD      R5, #%57
        LD      R6, #%1C            ; M_1C0E
        LD      R7, #%0E
        CALL    M_0910              ; init timer/interrupts
        SRP     #%60
        LD      R7, #%10            ; default char bitmap address starting at #1000
        LD      R12, #%68
        LD      R0, #%F7
        LD      R1, #%A0
        LD      R2, #%2D
        LD      R3, #%C3
        LDE     @RR0, R2            ; store 2dc3 at f7a0
        INC     R1
        LDE     @RR0, R3
        LD      R0, #%1F            ; { copy 1fe0-1fff to ffe0-ffff
        LD      R1, #%E0
        LD      R2, #%FF
        LD      R3, #%E0
M_08A0: LDE     R4, @RR0
        LDE     @RR2, R4
        INC     R1
        INC     R3
        .JP     NZ, M_08A0          ; }
        CLR     %8
        CLR     %7
        LD      %6, #%E0
        CALL    M_082D      ; print
        .data %0c "Willkommen am JTC : ES4.0 by SWB 1990\r\0"
        NOP
        NOP
        NOP
        JP      M_0949

        ; in: %53
        ;     %5B
        ;     %5C
        ; out: %4F
        ;      %51
M_08E0: PUSH    RP
        SRP     #%50
        OR      R3, R3
        .JP     NZ, M_08EF
M_08E8: LD      %51, R12
        LD      %4F, R11
M_08EC: POP     RP
        RET

M_08EF: DJNZ    R3, M_0901
        CP      %51, #%18       ; %53 was 1
        .JP     NC, M_08EC      ; >= 24
        CP      %4F, #%28
        .JP     NC, M_08EC      ; >= 40
        LD      R12, %51
        LD      R11, %4F
        .JP     M_08EC

M_0901: DJNZ    R3, M_08EC
        PUSH    %5B             ; %53 was 2
        PUSH    %5C
        LD      R12, %51
        LD      R11, %4F
        SRP     #%60
        .JP     M_0922

        NOP

        ; init timer/interrupts
M_0910: SRP     #%F0
        LD      R11, #%10       ; FB = IMR: enable IRQ4
        LD      R9, #8          ; F9 = IPR: A > B > C; A: IRQ5 > IRQ3; B: IRQ2 > IRQ0; C: IRQ1 > IRQ4
                                ; -> IRQ4 becomes least important IRQ
        LD      R8, #%92        ; F8 = P01M (default for 64k external memory)
        LD      R7, #1          ; F7 = P3M: Port 2 pull-up, parity off, P30 input, P31 digital, P32 input, P33 input, P34 output, P35 output, P36 output, P37 output
        LD      R5, #%41        ; F5 = PRE0: prescaler = 16
        LD      R4, #%FA        ; F4 = T0
        LD      R1, #3          ; F3 = TMR; load and enable T0
        EI
        RET


M_0922: CALL    M_1856          ; getScreenCharAddress in rr0
        LDE     R2, @RR0
        LD      %53, R2
        POP     %5C
        POP     %5B
        .JP     M_08EC

        .repeat 26
           NOP
        .end

        ; edi
M_0949: CALL    M_082D      ; print
        .data "Edi\r\0"
M_0951: OR      %55, #0b0100_0000
        LD      %58, #%FF
        SRP     #%10
        CALL    M_0815      ; GTC
        CP      %13, #%0D
        .JP     Z, M_0949
        LD      R6, %13
        CP      R6, #'?'
        .JP     NZ, M_0989
        CALL    M_0815      ; GTC
        LD      R5, %13
        LD      R6, %13
        RL      %16
        LD      R2, %9E(R6) ; load variable value into r2w, e.g. for var A, register pair %20 (%41 << 1 + %9E == %(1)20) is read
        LD      R3, %9F(R6)
        CALL    M_0818      ; PTC
        LD      R5, #'='
        CALL    M_0818      ; PTC
        CALL    M_0EE0
        LD      R5, #%0D
        CALL    M_0818      ; PTC
        JR      M_0951

M_0989: CP      R6, #'C'
        JR      NZ, M_0998
        TM      %0F, #8
        JR      Z, M_09CE
        AND     %0F, #%F7
        JR      M_09B4

M_0998: LD      %0F, #4
        CLR     %0E
        LD      R0, %6
        LD      R1, %7
        CP      R6, #'N'
        JR      NZ, M_09AC
        LD      R4, #0
        LDE     @RR0, R4
        JR      M_0949

M_09AC: CP      R6, #'R'
        JR      NZ, M_09E2
        CALL    %06C9
M_09B4: AND     %55, #%BF
        LD      %58, #%FF
        CALL    %0738
        OR      %55, #%40
        LD      R5, #%4F
        TM      %0F, #2
        JR      NZ, M_09D0
        LD      R5, #%53
        TM      %0F, #8
        JR      NZ, M_09D0
M_09CE: LD      R5, #%45
M_09D0: CALL    M_0818      ; PTC
        LD      R3, #%F0
        AND     %13, %0E
        SWAP    %13
M_09DA: CLR     %12
        CALL    M_0EE0
        JP      M_0949

M_09E2: CP      R6, #'E'
        JP      Z, M_0A79
        CP      R6, #'S'
        JR      NZ, M_0A0C
        ; S
        CALL    M_0EC5
        LD      %20, R0
        LD      %21, R1
        SUB     R3, R1
        SBC     R2, R0
        INCW    R2
        LD      %22, R2
        LD      %23, R3
        LD      %60, #%F7
        LD      %61, #1
        CALL    M_0821
M_0A07: LD      R3, %24
        JP      M_09DA

M_0A0C: CP      R6, #'L'
        JR      NZ, M_0A20
        LD      %20, R0
        LD      %21, R1
        LD      %60, #%F7
        LD      %61, #1
        CALL    M_0824
        JR      M_0A07

M_0A20: CP      R6, #'M'
        .JP     NZ, M_0E09
        CALL    M_082A
        OR      %55, #%40
        JP      M_0949

        NOP

        ; print char from %15
M_0A30: CALL    M_18FA
        RCF
        RET

M_0A35: TM      %55, #4
        JR      Z, M_0A3F
        CALL    M_1C19
        RCF
        RET

M_0A3F: CALL    M_081E
        LD      %5A, %13
        CALL    M_1905
        RCF
        RET

        .repeat 8
            NOP
        .end

        ; asciiToUInt
        ; in: rr14 = address to string with (up to 5 digit) number
        ; out: rr4 = number
        ;      rr14 = last non-digit address
M_0A52: LD      R12, #5
        LD      R4, #0
        LD      R5, #0
M_0A58: LDE     R13, @RR14
        SUB     R13, #'0'
        JR      C, M_0A77       ; < 0?
        CP      R13, #10
        JR      NC, M_0A77      ; >= 10
        LD      R10, #9         ; rr4 *= 10?
        LD      R2, R4
        LD      R3, R5
M_0A6A: ADD     R5, R3
        ADC     R4, R2
        DJNZ    R10, M_0A6A
        ADD     R5, R13
        ADC     R4, R10
        INC     R15
        DJNZ    R12, M_0A58
M_0A77: RCF
        RET


        ; Basic: find line
M_0A79: LD      R15, #1
        LD      R14, #%F7
        CALL    M_0A52      ; asciiToUInt
        OR      R4, #0b1000_0000
M_0A83: LDE     R10, @RR0
        INCW    R0
        LDE     R11, @RR0
        DECW    R0
        OR      R10, R10
        JR      Z, M_0AAA
        CP      R10, R4
        JR      C, M_0A9B
        JR      UGT, M_0AAD
        CP      R11, R5
        JR      Z, M_0AAD
        JR      UGT, M_0AAD
M_0A9B: INCW    R0
M_0A9D: INCW    R0
        LDE     R10, @RR0
        CP      R10, #%0D
        JR      NZ, M_0A9D
        INCW    R0
        JR      M_0A83

M_0AAA: JP      M_0951

M_0AAD: LDE     R2, @RR0
        TM      R2, #0b1000_0000
        JR      Z, M_0AAA
        INCW    R0
        LDE     R3, @RR0
        AND     R2, #0b0111_1111
        CALL    M_0EE0
M_0ABE: INCW    R0
        LDE     R5, @RR0
        CP      R5, #%0D
        JR      Z, M_0ACA
        OR      %55, #0b1000_0000
M_0ACA: CALL    M_0818      ; PTC
        CP      R5, #%0D
        JR      NZ, M_0ABE
        CALL    M_081E
        CP      R3, #' '
        JR      NZ, M_0AAA
        INCW    R0
        JR      M_0AAD

        NOP
        NOP

        ; Mon, F(ill)
        ; F<word address> <word length> <byte value>
        ; e.g. F8000 0010 ff  (fill 16 bytes from 0x8000 with 0xFF)
M_0AE0: CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        LD      R8, R10
        LD      R9, R11
        CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        CALL    M_0CDC      ; getHexByteFromRR14
        JR      C, M_0AFF   ; no hex digit -> ret
M_0AEF: LDE     @RR8, R13
        INCW    R8
M_0AF3: DECW    R10
        JR      NZ, M_0AEF
M_0AF7: CALL    M_082D      ; print
        .data "Mon\r\0"
M_0AFF: ret

        ; data read from M_0FCA
M_0B00: .data %00 %20 %40 %60 %80 %A0 %01 %21  %41 %61 %81 %A1 %02 %22 %42 %62
        .data %82 %A2 %03 %23 %43 %63 %83 %A3  %04 %24 %44 %64 %84 %A4 %05 %25
        .data %45 %65 %85 %A5 %06 %26 %46 %66  %86 %A6 %07 %27 %47 %67 %87 %A7
        .data %08 %28 %48 %68 %88 %A8 %09 %29  %49 %69 %89 %A9 %0A %2A %4A %6A
        .data %8A %AA %0B %2B %4B %6B %8B %AB  %0C %2C %4C %6C %8C %AC %0D %2D
        .data %4D %6D %8D %AD %0E %2E %4E %6E  %8E %AE %0F %2F %4F %6F %8F %AF
        .data %10 %30 %50 %70 %90 %B0 %11 %31  %51 %71 %91 %B1 %12 %32 %52 %72
        .data %92 %B2 %13 %33 %53 %73 %93 %B3  %14 %34 %54 %74 %94 %B4 %15 %35
        .data %55 %75 %95 %B5 %16 %36 %56 %76  %96 %B6 %17 %37 %57 %77 %97 %B7
        .data %18 %38 %58 %78 %98 %B8 %19 %39  %59 %79 %99 %B9 %1A %3A %5A %7A
        .data %9A %BA %1B %3B %5B %7B %9B %BB  %1C %3C %5C %7C %9C %BC %1D %3D
        ; data read from M_0FCA
M_0BC0: .data %5D %7D %9D %BD %1E %3E %5E %7E  %9E %BE %1F %3F %5F %7F %9F %BF
        .data %00 %28 %50 %80 %A8 %D0 %FF %FF  %80 %08 %20 %02 %40 %04 %10 %01

        ; Mon, R(egister)
        ; R<byte register>
        ; e.g. R10 (print content from register %10)
M_0BD0: CALL    M_0CDC      ; getHexByteFromRR14
        JR      C, M_0BE4   ; no hex digit -> ret
        LD      R10, R13
M_0BD7: LD      R11, @R10
        LD      R5, #'!'
        CALL    M_0C9B      ; printCharWord
        INC     R10
        CALL    M_0C91
        JR      Z, M_0BD7
M_0BE4: RET

        ; Mon, !
        ; !<register><value>
        ; e.g. !100b (store value %0b in register %10)
M_0BE5: CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        LD      @R10, R11
        INC     R10
        LD      R5, #'!'
        CALL    M_0818      ; PTC
        LD      R9, R10
        JP      M_0C72      ; printHexByte
M_0BF5: JP      M_0F37

        ; Mon
M_0BF8: CALL    M_0AF7      ; printMon
        PUSH    RP
        SRP     #%60
        LD      R0, #%F7    ; { copy registers %10-%1F to %F780-%F78F
        LD      R1, #%80
        LD      R2, #%10
        LD      R3, #%10
M_0C07: LDEI    @RR0, @R2
        DJNZ    R3, M_0C07  ; }
        SRP     #%10
M_0C0D: LD      %58, #%FF
        AND     %55, #0b1011_1111
        CALL    M_0815      ; GTC
        LD      R15, #0
        LD      R14, #%F7
//M_0C1A:
        LDE     R0, @RR14
        INC     R15
        LD      R12, #%0C   ; read data from 0c39 into r1
        LD      R13, #%39
M_0C21: LD      R1, #%10
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
        JR      M_0BF5

M_0C39: ; data read from M_0C1A
        ; value  address
        .data 'F' M_0AE0
        .data 'R' M_0BD0
        .data 'H' M_0CF2
        .data ',' M_0D10
        .data 'J' M_0D42
        .data 'A' M_0D26
        .data ';' M_0D6F
        .data 'S' M_0D56
        .data 'L' M_0D93
        .data 'M' M_0F7D
        .data '?' M_0DC6
        .data 'Q' M_0CEB
        .data '!' M_0BE5
        .data '#' M_0DE5
        .data '%' M_1ED0
        .data %FF %FFFF

        ; printHexWord
        ; in: rr8
M_0C69: PUSH    R9
        LD      %19, R8
        CALL    M_0C72      ; printHexByte
        POP     R9
        ; printHexByte
        ; in: r9
M_0C72: PUSH    R9
        SWAP    R9
        CALL    M_0C7B      ; printHexLowerNibble
        POP     R9
        ; printHexLowerNibble
        ; in: r9
M_0C7B: LD      R5, #%0F
        AND     R5, R9
        ADD     R5, #'0'
        CP      R5, #%3A
        JR      C, M_0C8A
        ADD     R5, #7
M_0C8A: JP      M_0818      ; PTC

        ; println
M_0C8D: LD      R5, #%0D
        JR      M_0C8A

M_0C91: CALL    M_0C8D      ; println
        CALL    M_081E
        JP      M_0DFA

        NOP

        ; printCharWord
        ; in: %15: char
        ;     rr10: word
M_0C9B: CALL    M_0818      ; PTC
        LD      %18, R10
        LD      %19, R11
        CALL    M_0C69      ; printHexWord
        LD      R5, #%20
        JR      M_0C8A

        ; getHexWordFromRR14_forgetCallerIfError
        ; in: rr14 = address
        ; out: rr10 = entered word
        ; in case of error, the calling method is forgotten
M_0CA9: CALL    M_0CD5      ; getHexWordFromRR14
        JR      NC, M_0CB2
        POP     R0          ; no hex digit? forget caller
        POP     R0
M_0CB2: LD      R10, R12
        LD      R11, R13
        INC     R15
        RET

        ; getHexDigitFromRR14
        ; in: rr14 = address
        ; out: C = 1 -> no hex digit
        ;      rr14 =+ 1
        ;      r13 = hex digit (00..0F)
M_0CB8: LDE     R13, @RR14
        INC     R15
        CP      R13, #'0'
        JR      C, M_0CD4
        SUB     R13, #%30
        CP      R13, #10
        JR      C, M_0CD3
        CP      R13, #%11
        JR      C, M_0CD4
        SUB     R13, #7
        CP      R13, #%10
M_0CD3: CCF
M_0CD4: RET

        ; getHexWordFromRR14
        ; in: rr14 = address
        ; expects: SRP==%10
        ; out: C = 1 -> no hex digit
        ;      rr14 =+ 1, 2, 3 or 4
        ;      %1C = hex value
        ;      r13 = hex digit (00..FF)
M_0CD5: CALL    M_0CDC      ; getHexByteFromRR14
        JR      C, M_0CEA   ; no hex digit -> ret
        LD      %1C, R13

        ; getHexByteFromRR14
        ; in: rr14 = address
        ; out: C = 1 -> no hex digit
        ;      r14w =+ 1 or 2
        ;      r13 = hex digit (00..FF)
M_0CDC: CALL    M_0CB8      ; getHexDigitFromRR14
        JR      C, M_0CEA   ; no digit -> ret
        LD      R2, R13
        SWAP    R2
        CALL    M_0CB8      ; getHexDigitFromRR14
        OR      R13, R2
M_0CEA: RET

        ; Mon, Q(uit)
M_0CEB: POP     R0
        POP     R0
        JP      M_0DB5

        ; Mon, H(ex)
        ; H<word address>
        ; e.g. H8000 (list 8 memory bytes from address 0x8000)
M_0CF2: CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
M_0CF5: LD      R5, #','
        CALL    M_0C9B      ; printCharWord
        LD      R0, #8
M_0CFC: LDE     R9, @RR10
        INCW    R10
        CALL    M_0C72      ; printHexByte
        LD      R5, #' '
        CALL    M_0818      ; PTC
        DJNZ    R0, M_0CFC
        CALL    M_0C91
        JR      Z, M_0CF5
M_0D0F: RET

        ; Mon, ,
        ; ,<word address> <byte>...
M_0D10: CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        LD      R0, #8
M_0D15: CALL    M_0CDC      ; getHexByteFromRR14
        JR      C, M_0D0F
        INC     R15
        LDE     @RR10, R13
        INCW    R10
        DJNZ    R0, M_0D15
        LD      R5, #','
M_0D23: JP      M_0C9B      ; printCharWord

        ; Mon, A
        ; A<word address>
        ; e.g. A8000 (print 16 characters starting from address %8000)
M_0D26: CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
M_0D29: LD      R5, #';'
        CALL    M_0C9B      ; printCharWord
        LD      R0, #%10
M_0D30: LDE     R5, @RR10
        OR      %55, #0b1000_0000
        INCW    R10
        CALL    M_0818      ; PTC
        DJNZ    R0, M_0D30
        CALL    M_0C91
        JR      Z, M_0D29
        RET

        ; Mon, J(ump)
        ; J<word>
        ; e.g. J000c (jump to address %000c)
M_0D42: CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        LD      FLAGS, R6
        LD      RP, %17
        CALL    @%1A
        LD      %17, RP
        SRP     #%10
        LD      R6, FLAGS
        JP      M_0AF7

        ; Mon, S(ave)
        ; S<word address> <word amount>
M_0D56: CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        LD      %20, R10
        LD      %21, R11
        CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        LD      %22, R10
        LD      %23, R11
        LD      %60, R14
        LD      %61, R15
        CALL    M_0821
        JR      M_0DAD

        NOP
        NOP

        ; Mon, ;
        ; ;<word> <char>...
        ; e.g. ;8000 hello (save the text "hello" without trailing \0 at address 8000)
M_0D6F: CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        LD      R0, #%10
M_0D74: LDE     R2, @RR14
        LDE     @RR10, R2
        CP      R2, #%0D
        JR      Z, M_0D86
        INC     R15
        INCW    R10
        DJNZ    R0, M_0D74
        LD      R5, #';'
        JR      M_0D23

M_0D86: RET

        .repeat 12
            NOP
        .end

        ; L
M_0D93: CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        LD      %20, R10
        LD      %21, R11
        LD      %60, R14
        LD      %61, R15
        CALL    M_0824
        LD      R8, %22
        LD      R9, %23
        CALL    M_0C69      ; printHexWord
        LD      R5, #%20
        CALL    M_0818      ; PTC
M_0DAD: LD      R9, %24
        CALL    M_0C72      ; printHexByte
M_0DB2: JP      M_0AF7

M_0DB5: SRP     #%60
        LD      R0, #%F7
        LD      R1, #%80
        LD      R2, #%10
        LD      R3, #%10
M_0DBF: LDEI    @R2, @RR0
        DJNZ    R3, M_0DBF
        POP     RP
        RET

        ; ?
M_0DC6: CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        CALL    M_0CD5      ; getHexWordFromRR14
        JR      C, M_0DE4   ; no hex digit -> ret
        LD      R8, R10
        LD      R9, R11
        ADD     R9, R13
        ADC     R8, R12
        CALL    M_0C69      ; printHexWord
        LD      R5, #%20
        SUB     R11, R13
        SBC     R10, R12
        CALL    M_0C9B      ; printCharWord
        JR      M_0DB2

M_0DE4: RET

        ; #
M_0DE5: CALL    M_0A52
        LD      R10, %14
        LD      R11, %15
        LD      R5, #'%'
        CALL    M_0C9B      ; printCharWord
        JP      M_0C8D      ; println

        .repeat 6
            NOP
        .end

M_0DFA: CP      %13, #' '
        JR      Z, M_0E06
        LD      %5A, R3
        CALL    M_1905
        OR      R3, R3
M_0E06: RET
        NOP
        NOP
M_0E09: CP      R6, #%3A
M_0E0C: JP      NC, M_0951
        CP      R6, #'0'
        CCF
        JR      NC, M_0E0C
        LD      R15, #0
        LD      R14, #%F7
        CALL    M_0A52
        LD      %60, R15
        LD      %61, R15
        COM     %61
        ADD     %61, #5
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
M_0E80: DEC     R15
        LD      R14, %58
        CP      R14, R15
        JP      LE, M_0951
        LD      R7, %61
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
        LD      R3, %60
        SUB     R14, R15
M_0EB3: LDE     R10, @RR2
        LDE     @RR0, R10
        INCW    R0
        INC     R3
        DEC     %1E
        JR      NZ, M_0EB3
        LD      R10, #%0D
        LDE     @RR0, R10
        JP      M_0951

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

        ; printInt (decimal) without leading ' '
        ; input: rr2
        ; assumes RP==%10
M_0EE0: CALL    %0182       ; intToAscii (decimal)
        CP      R4, #' '
        JR      Z, M_0EF1
        PUSH    %15
        LD      R5, R4
        CALL    M_0818      ; PTC
        POP     %15
M_0EF1: LD      R11, #%15
M_0EF3: CP      @%1B, #'0'  ; r11==%1B
        JR      NZ, M_0EFE
        INC     R11
        CP      R11, #%19   ; < 0x19
        JR      C, M_0EF3
M_0EFE: LD      R5, @R11
        CALL    M_0818      ; PTC
        INC     R11
        CP      R11, #%1A
        JR      C, M_0EFE
        RET

        ; in: %4E
        ;     %4F
        ;     %51
        ; out: %53
M_0F0A: PUSH    RP
        SRP     #%60
        CALL    M_0FBB
        CLR     %53
        LD      R4, #%60
        LD      R5, #%EF
        LD      P01M, #%B2
M_0F1A: TM      %3, #0b0000_0100        ; wait until P32(BUSY) == 0
        JR      NZ, M_0F1A
M_0F1F: LDE     @RR4, R5
        LDE     R9, @RR0
        RCF
        TM      R9, R3
        JR      Z, M_0F29
        SCF
M_0F29: RRC     %53
        RL      R5
        JR      C, M_0F1F
        LD      P01M, #%92
        SWAP    %53
        POP     RP
M_0F36: RET

M_0F37: LD      R12, #%80
        LD      R13, #0
M_0F3B: LDE     R2, @RR12
        CP      R2, R0
        JR      Z, M_0F4E
M_0F41: AND     R13, #%F0
        ADD     R13, #%10
        ADC     R12, #0
        JR      Z, M_0F7A
        JR      M_0F3B
M_0F4E: INC     R13
        LD      R1, #3
M_0F51: LDE     R2, @RR12
        CP      R2, #%95
        JR      NZ, M_0F41
        INC     R13
        DJNZ    R1, M_0F51
        LD      R1, #4
        LD      R2, #%18
M_0F5F: LDEI    @R2, @RR12
        DJNZ    R1, M_0F5F
        LD      R2, R12
        LD      R3, R13
M_0F67: LDE     R1, @RR2
        ADD     R11, R1
        ADC     R10, #0
        INCW    R2
        DECW    R8
        JR      NZ, M_0F67
        OR      R10, R11
        JR      NZ, M_0F41
        CALL    @%1C
M_0F7A: JP      M_0C0D

        ; M
M_0F7D: CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        LD      R8, R10
        LD      R9, R11
        CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        CALL    M_0CD5      ; getHexWordFromRR14
        JR      C, M_0F36
        CP      R8, R10
        JR      UGT, M_0FAC
        JR      C, M_0F96
        CP      R9, R11
        JR      NC, M_0FAC
M_0F96: ADD     R9, R13
        ADC     R8, R12
        ADD     R11, R13
        ADC     R10, R12
M_0F9E: DECW    R8
        DECW    R10
        LDE     R0, @RR8
        LDE     @RR10, R0
        DECW    R12
        JR      NZ, M_0F9E
        JR      M_0FB8

M_0FAC: LDE     R0, @RR8
        LDE     @RR10, R0
        INCW    R8
        INCW    R10
        DECW    R12
        JR      NZ, M_0FAC
M_0FB8: JP      M_0AF7

        ; in: %4E/%4F  = x_pixel
        ;     %51      = y_pixel
        ; out: %50/%51
        ;      %53
        ; expects RP==%50
M_0FBB: CP      %51, #%C0   ; >= 192
        JR      NC, M_1000
        OR      %4E, %4E    ; == 0
        JR      Z, M_0FCA
        CP      %4F, #%40   ; >= 64/'@'
        JR      NC, M_1000
M_0FCA: LD      R11, %51    ; { ld r0, 0B00+%51
        LD      R10, #%0B
        LDC     R0, @RR10   ; }
        LD      R3, #0b1110_0000
        AND     R3, R0
        SWAP    R3
        RR      R3
        ADD     R3, #%C0
        LD      R2, #%0B
        LDC     R1, @RR2
        LD      R2, %4E
        LD      R3, #%19    ; 0b0001_1001
        LD      R10, %4F
        RRC     R2
        RRC     R10
        RLC     R3
        RRC     R10
        RLC     R3
        RRC     R10
        RLC     R3
        ADD     R1, R10
        LD      R2, #%0B
        LDC     R3, @RR2
        AND     R0, #%1F    ; 0b0001_1111
        ADD     R0, #%40
        RET

        ; return invisible address in case of invalid x,y
M_1000: LD      R0, #%40
        LD      R1, #%79
        LD      R3, #1
        RET

        NOP

        ; char bitmap data
        .data %00 %18 %30 %7E %30 %18 %00 %00     ; 0x01
        .data %00 %18 %0C %7E %0C %18 %00 %00     ; 0x02
        .data %00 %18 %3C %7E %18 %18 %18 %00     ; 0x03
        .data %00 %18 %18 %18 %7E %3C %18 %00     ; 0x04
        .data %F8 %E0 %F0 %D8 %CC %06 %03 %00     ; 0x05
        .data %C3 %C7 %CF %DF %DF %CF %C7 %C3     ; 0x06
        .data %0C %1C %3C %7C %3C %1C %0C %00     ; 0x07
        .data %30 %60 %FE %60 %30 %00 %FE %FE     ; 0x08
        .data %30 %38 %3C %3E %3C %38 %30 %00     ; 0x09
        .data %00 %00 %18 %3C %7E %FF %00 %00     ; 0x0a
        .data %00 %00 %FF %7E %3C %18 %00 %00     ; 0x0b
        .data %FF %C3 %C3 %C3 %C3 %C3 %C3 %FF     ; 0x0c
        .data %00 %33 %63 %FF %60 %30 %00 %00     ; 0x0d
        .data %F8 %C0 %F8 %C0 %FE %18 %1E %00     ; 0x0e
        .data %FF %FF %FF %FF %FF %FF %FF %FF     ; 0x0f

        .data %7E %66 %66 %66 %66 %66 %7E %00     ; 0x10
        .data %06 %06 %06 %06 %06 %06 %06 %00     ; 0x11
        .data %7E %06 %06 %7E %60 %60 %7E %00     ; 0x12
        .data %7E %06 %06 %7E %06 %06 %7E %00
        .data %66 %66 %66 %7E %06 %06 %06 %00
        .data %7E %60 %60 %7E %06 %06 %7E %00
        .data %7E %60 %60 %7E %66 %66 %7E %00
        .data %7E %06 %06 %06 %06 %06 %06 %00
        .data %7E %66 %66 %7E %66 %66 %7E %00     ; 0x18
        .data %7E %66 %66 %7E %06 %06 %7E %00
        .data %00 %66 %00 %3E %66 %66 %3E %00
        .data %00 %66 %00 %3C %66 %66 %3C %00
        .data %00 %66 %00 %66 %66 %66 %3E %00
        .data %66 %00 %3C %66 %66 %7E %66 %00
        .data %66 %00 %3C %66 %66 %66 %3C %00
        .data %66 %00 %66 %66 %66 %66 %3C %00

        .data %00 %00 %00 %00 %00 %00 %00 %00     ; 0x20 space
        .data %00 %18 %18 %18 %18 %00 %18 %00     ; !
        .data %00 %66 %66 %66 %00 %00 %00 %00     ; "
        .data %00 %66 %FF %66 %66 %FF %66 %00     ; #
        .data %18 %3E %60 %3C %06 %7C %18 %00     ; $
        .data %00 %66 %6C %18 %30 %66 %46 %00
        .data %1C %36 %1C %38 %6F %66 %3B %00
        .data %00 %18 %18 %18 %00 %00 %00 %00
        .data %00 %0E %1C %18 %18 %1C %0E %00     ; 0x28
        .data %00 %70 %38 %18 %18 %38 %70 %00
        .data %00 %66 %3C %FF %3C %66 %00 %00
        .data %00 %18 %18 %7E %18 %18 %00 %00
        .data %00 %00 %00 %00 %00 %18 %18 %30
        .data %00 %00 %00 %7E %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %18 %18 %00
        .data %00 %06 %0C %18 %30 %60 %40 %00

        .data %00 %3C %66 %6E %76 %66 %3C %00     ; 0x30 0
        .data %00 %18 %38 %18 %18 %18 %7E %00     ; 1
        .data %00 %3C %66 %0C %18 %30 %7E %00     ; 2
        .data %00 %7E %0C %18 %0C %66 %3C %00     ; 3
        .data %00 %0C %1C %3C %6C %7E %0C %00     ; 4
        .data %00 %7E %60 %7C %06 %66 %3C %00     ; 5
        .data %00 %3C %60 %7C %66 %66 %3C %00     ; 6
        .data %00 %7E %06 %0C %18 %30 %30 %00     ; 7
        .data %00 %3C %66 %3C %66 %66 %3C %00     ; 8
        .data %00 %3C %66 %3E %06 %0C %38 %00     ; 9
        .data %00 %00 %18 %18 %00 %18 %18 %00     ; :
        .data %00 %00 %18 %18 %00 %18 %18 %30     ; ;
        .data %06 %0C %18 %30 %18 %0C %06 %00
        .data %00 %00 %7E %00 %00 %7E %00 %00
        .data %60 %30 %18 %0C %18 %30 %60 %00
        .data %00 %3C %66 %0C %18 %00 %18 %00

        .data %00 %3C %66 %6E %6E %60 %3E %00     ; 0x40
        .data %00 %18 %3C %66 %66 %7E %66 %00     ; A
        .data %00 %7C %66 %7C %66 %66 %7C %00
        .data %00 %3C %66 %60 %60 %66 %3C %00
        .data %00 %78 %6C %66 %66 %6C %78 %00
        .data %00 %7E %60 %7C %60 %60 %7E %00
        .data %00 %7E %60 %7C %60 %60 %60 %00
        .data %00 %3E %60 %60 %6E %66 %3E %00
        .data %00 %66 %66 %7E %66 %66 %66 %00
        .data %00 %7E %18 %18 %18 %18 %7E %00
        .data %00 %06 %06 %06 %06 %66 %3C %00
        .data %00 %66 %6C %78 %78 %6C %66 %00
        .data %00 %60 %60 %60 %60 %60 %7E %00
        .data %00 %63 %77 %7F %6B %63 %63 %00
        .data %00 %66 %76 %7E %7E %6E %66 %00
        .data %00 %3C %66 %66 %66 %66 %3C %00

        .data %00 %7C %66 %66 %7C %60 %60 %00     ; 0x50
        .data %00 %3C %66 %66 %66 %6C %36 %00
        .data %00 %7C %66 %66 %7C %6C %66 %00
        .data %00 %3C %60 %3C %06 %06 %3C %00
        .data %00 %7E %18 %18 %18 %18 %18 %00
        .data %00 %66 %66 %66 %66 %66 %7E %00
        .data %00 %66 %66 %66 %66 %3C %18 %00
        .data %00 %63 %63 %6B %7F %77 %63 %00
        .data %00 %66 %66 %3C %3C %66 %66 %00
        .data %00 %66 %66 %3C %18 %18 %18 %00
        .data %00 %7E %0C %18 %30 %60 %7E %00
        .data %00 %1E %18 %18 %18 %18 %1E %00
        .data %00 %40 %60 %30 %18 %0C %06 %00
        .data %00 %78 %18 %18 %18 %18 %78 %00
        .data %00 %08 %1C %36 %63 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %FF %00

        .data %00 %18 %3C %7E %7E %3C %18 %00     ; 0x60
        .data %00 %00 %3C %06 %3E %66 %3E %00
        .data %00 %60 %60 %7C %66 %66 %7C %00
        .data %00 %00 %3C %60 %60 %60 %3C %00
        .data %00 %06 %06 %3E %66 %66 %3E %00
        .data %00 %00 %3C %66 %7E %60 %3C %00
        .data %00 %0E %18 %3E %18 %18 %18 %00
        .data %00 %00 %3E %66 %66 %3E %06 %7C
        .data %00 %60 %60 %7C %66 %66 %66 %00
        .data %00 %18 %00 %38 %18 %18 %3C %00
        .data %00 %06 %00 %06 %06 %06 %06 %3C
        .data %00 %60 %60 %6C %78 %6C %66 %00
        .data %00 %38 %18 %18 %18 %18 %3C %00
        .data %00 %00 %66 %7F %7F %6B %63 %00
        .data %00 %00 %7C %66 %66 %66 %66 %00
        .data %00 %00 %3C %66 %66 %66 %3C %00

        .data %00 %00 %7C %66 %66 %7C %60 %60     ; 0x70
        .data %00 %00 %3E %66 %66 %3E %06 %06
        .data %00 %00 %7C %66 %60 %60 %60 %00
        .data %00 %00 %3E %60 %3C %06 %7C %00
        .data %00 %18 %7E %18 %18 %18 %0E %00
        .data %00 %00 %66 %66 %66 %66 %3E %00
        .data %00 %00 %66 %66 %66 %3C %18 %00
        .data %00 %00 %63 %6B %7F %3E %36 %00
        .data %00 %00 %66 %3C %18 %3C %66 %00
        .data %00 %00 %66 %66 %66 %3E %0C %78
        .data %00 %00 %7E %0C %18 %30 %7E %00
        .data %0C %18 %18 %30 %18 %18 %0C %00
        .data %18 %18 %18 %00 %18 %18 %18 %00
        .data %30 %18 %18 %0C %18 %18 %30 %00
        .data %00 %00 %71 %DB %8E %00 %00 %00
        .data %00 %7C %66 %7C %66 %7C %60 %60

M_1400: PUSH    RP
        SRP     #%60
        LD      R1, #4
        LD      R2, #%0C
M_1408: PUSH    @%61
        INC     R1
        DJNZ    R2, M_1408
        SRP     #0
        LD      R11, %60
        LD      R4, #%F7
        LD      R5, #%A4
        LDE     R13, @RR4
        LD      R5, #%A6
        LDE     R15, @RR4
        INC     R15
        LDE     @RR4, R15
        LD      R4, #%1C
        LD      R5, #0
M_1422: DECW    %4
        JR      NZ, M_1422
        LD      R4, #%F5
        LD      R5, #%FF
        CP      %0B, #%FA
        JR      NZ, M_1434
        AND     %0D, #%7F
        LDE     @RR4, R13
M_1434: LD      R5, #%7F
        LDE     @RR4, R11
        LD      R6, #0
        RCF
M_143B: LDE     R7, @RR4
        ADC     R6, R7
        INC     R5
        JR      NZ, M_143B
        ADC     %6, #0
        LD      R5, #%7B
        .repeat 4
            NOP
        .end
        LDE     @RR4, R15
        INC     R5
        LDE     @RR4, R15
        INC     R5
        LDE     @RR4, R6
        INC     R5
        LDE     @RR4, R6
        LD      R5, #%7B
        LD      TMR, #3
        LD      R3, #%40
        CALL    M_14A4
        LD      R10, #%40
        LD      R3, #0
        LD      R6, #4
M_1466: CALL    M_14A4
        DJNZ    R6, M_1466
M_146B: LDE     R6, @RR4
        LD      R7, #8
M_146F: RL      %6
        XOR     R3, R10
        CALL    M_14A4
        JR      NC, M_147B
        CALL    M_14A4
M_147B: XOR     R3, R10
        CALL    M_14A4
        JR      NC, M_1485
        CALL    M_14A4
M_1485: DJNZ    R7, M_146F
        INC     R5
        JR      NZ, M_146B
        XOR     R3, R10
        CALL    M_14A4
        LD      TMR, #%43
        SRP     #%60
        LD      R1, #%0F
        LD      R2, #%0C
M_1498: POP     @%61
        DEC     %61
        DJNZ    R2, M_1498
        POP     RP
        RET

        NOP
        NOP
        NOP

M_14A4: LD      %60, #%18
M_14A7: DEC     %60
        JR      NZ, M_14A7
        RET

        NOP
        NOP

M_14AE: PUSH    RP
        SRP     #%60
        LD      R1, #4
        LD      R2, #%0C
M_14B6: PUSH    @%61
        INC     R1
        DJNZ    R2, M_14B6
        SRP     #0
        LD      R4, #%F7
        LD      R5, #%A6
        LDE     R6, @RR4
        INC     R6
        LDE     @RR4, R6
        LD      %5E, R6
M_14C8: LD      R4, #%F5
        LD      R5, #%7B
        LD      R10, #%29
        LD      R9, #%21
        LD      R6, #1
        AND     R6, R3
M_14D4: LD      R13, #1
        AND     R13, R3
        CP      R13, R6
        JR      Z, M_14D4
M_14DC: LD      R6, #1
        AND     R6, R3
        LD      R7, #%41
M_14E2: DEC     %7
        JR      Z, M_14DC
        LD      R13, #1
        AND     R13, R3
        CP      R13, R6
        JR      Z, M_14E2
        CP      R7, R9
        JR      NC, M_14DC
M_14F2: LD      R8, #8
M_14F4: LD      R7, #0
        LD      R6, #1
        AND     R6, R3
M_14FA: INC     R7
        LD      R13, #1
        AND     R13, R3
        CP      R13, R6
        JR      Z, M_14FA
        LD      R6, #1
        AND     R6, R3
M_1507: INC     R7
        LD      R13, #1
        AND     R13, R3
        CP      R13, R6
        JR      Z, M_1507
        CP      R10, R7
        RLC     %9
        DJNZ    R8, M_14F4
        LDE     @RR4, R9
        INC     R5
        JR      NZ, M_14F2
        LD      R5, #%7D
        LD      R7, #0
        LDE     R10, @RR4
        INC     R5
        LDE     R9, @RR4
        INC     R5
        RCF
M_1526: LDE     R6, @RR4
        ADC     R7, R6
        INC     R5
        JR      NZ, M_1526
        ADC     %7, #0
        CP      R7, R9
        JR      NZ, M_1577
        CP      R7, R10
        JR      NZ, M_1577
        LD      R5, #%7B
        LDE     R9, @RR4
        INC     R5
        LDE     R10, @RR4
        NOP
        NOP
        CP      %9, %5E
        JR      NZ, M_1577
        XOR     R9, R10
        JR      NZ, M_1577
        LD      R5, #%7F
        LDE     R7, @RR4
        LD      R11, #0
        CP      %7, #%FC
        JR      Z, M_1572
        LD      R5, #%FF
        LDE     R8, @RR4
        LD      R11, #%80
        OR      R11, R8
        CP      %7, #%FA
        JR      Z, M_1572
        CP      %7, #%FE
        JR      NZ, M_1577
        LD      R14, #%88
M_1569: SCF
M_156A: LD      TMR, #3
        LD      %13, R14
        JR      M_1591

        NOP
M_1572: NOP
        NOP
        RCF
        JR      M_156A

M_1577: LD      TMR, #%43
M_157A: CALL    M_081B
        AND     %6D, #%DF
        CP      %6D, #%42
        JR      Z, M_158D
        CP      %6D, #%43
        JR      NZ, M_157A
        JP      M_14C8

M_158D: LD      R14, #%FF
        JR      M_1569

M_1591: LD      R4, #%F7
        LD      R5, #%A5
        LDE     @RR4, R11
        SRP     #%60
        LD      R1, #%0F
        LD      R2, #%0C
M_159D: POP     @%61
        DEC     %61
        DJNZ    R2, M_159D
        POP     RP
        RET

        NOP
        NOP

M_15A8: PUSH    RP
        SRP     #%60
        LD      R2, #%F7
        LD      R3, #%A7
        LDE     R4, @RR2
        TM      %64, #2
        JR      Z, M_15CA
        LD      R3, #%A4
        LDE     R4, @RR2
        CP      %64, #%80
        JR      Z, M_15C5
        LD      R0, #%FA
        CALL    M_1400
M_15C5: LD      R0, #%FE
        CALL    M_1400
M_15CA: CALL    M_0910          ; init timer/interrupts
        POP     RP
        RCF
        RET

        NOP

M_15D2: PUSH    RP
        DI
        SRP     #%F0
        LD      R8, #%92
        LD      R4, #%64
        LD      R5, #5
        LD      R1, #%43
        LD      R7, #8
        SRP     #%60
        LD      R2, #%F7
        LD      R3, #%A6
        LD      R4, #%FF
        LDE     @RR2, R4
        INC     R3
        LD      R4, %15
        LDE     @RR2, R4
        TM      %15, #2
        JR      NZ, M_1602
        LD      TMR, #3
        LD      R4, #0
M_15FA: LD      R3, #%A4
        LDE     @RR2, R4
        POP     RP
        RCF
        RET

M_1602: LD      R0, #0
        LD      R1, #0
M_1606: LD      R5, #%18
M_1608: DJNZ    R5, M_1608
        DECW    %60
        JR      NZ, M_1606
        LD      R4, #%80
        JR      M_15FA

        NOP
        NOP

M_1614: PUSH    RP
        SRP     #%60
        LD      R0, #%F7
        LD      R1, #%A4
        LDE     R3, @RR0
        LD      R2, #%F5
        LD      R4, #%15
        LDEI    @RR2, @R4
        OR      R3, R3
        JR      NZ, M_1633
        LD      R0, #%FC
        CALL    M_1400
        LD      R3, #%80
        LD      R0, #%F7
        LD      R1, #%A4
M_1633: LDE     @RR0, R3
        RCF
        POP     RP
        RET

        NOP

        ; _print
        ; in: string ending with \0
        ; destroys %70, %71
M_163A: POP     %70
        POP     %71
        PUSH    RP
        SRP     #%70
M_1642: LD      R2, #%15
        LDEI    @R2, @RR0
        OR      %15, %15
        JR      Z, M_1650
        CALL    M_0818      ; PTC
        JR      M_1642

M_1650: POP     RP
        JP      @%70

        .repeat 4
            NOP
        .end

        LD      %51, R7
        LD      %4E, R4
        LD      %4F, R5
        JP      M_17FD

        LD      %51, R5
        LD      %4E, R6
        LD      %4F, R7
        JP      M_17FD

M_166A: PUSH    %4E
        PUSH    %4F
        PUSH    %51
        PUSH    RP
        SRP     #%70
        LD      R0, %4E
        LD      R1, %4F
        LD      R2, #0
        LD      R3, %51
        LD      R4, %4A
        LD      R5, %4B
        LD      R6, #0
        LD      R7, %4D
        LD      %5E, #%16
        LD      %5F, #%58
        LD      R9, R5
        SUB     R9, R1
        LD      R8, R4
        SBC     R8, R0
        JR      PL, M_169A
        COM     R9
        COM     R8
        INCW    R8
M_169A: LD      R11, R7
        SUB     R11, R3
        LD      R10, R6
        SBC     R10, R2
        JR      PL, M_16AA
        COM     R11
        COM     R10
        INCW    R10
M_16AA: CP      R9, R11
        LD      R12, R8
        SBC     R12, R10
        JR      NC, M_16C8
        LD      %5E, #%16
        LD      %5F, #%61
        LD      R0, #0
        LD      R1, %51
        LD      R2, %4E
        LD      R3, %4F
        LD      R4, #0
        LD      R5, %4D
        LD      R6, %4A
        LD      R7, %4B
M_16C8: CP      R5, R1
        LD      R12, R4
        SBC     R12, R0
        JR      C, M_16E8
        LD      R12, R1
        LD      R1, R5
        LD      R5, R12
        LD      R12, R0
        LD      R0, R4
        LD      R4, R12
        LD      R12, R3
        LD      R3, R7
        LD      R7, R12
        LD      R12, R2
        LD      R2, R6
        LD      R6, R12
M_16E8: SUB     R1, R5
        SBC     R0, R4
        LD      R10, R0
        LD      R11, R1
        LD      R9, #0
        SUB     R3, R7
        SBC     R2, R6
        JR      PL, M_1700
        LD      R9, #%FF
        COM     R2
        COM     R3
        INCW    R2
M_1700: LD      R12, #0
        LD      R13, #0
        LD      R14, #0
        LD      R15, #9
M_1708: SUB     R3, R1
        SBC     R2, R0
        JR      NC, M_1713
        ADD     R3, R1
        ADC     R2, R0
        SCF
M_1713: CCF
        RLC     R14
        RLC     R13
        RLC     R12
        RCF
        RLC     R3
        RLC     R2
        DJNZ    R15, M_1708
        OR      R9, R9
        JR      PL, M_1730
        COM     R14
        COM     R13
        COM     R12
        INC     R14
        JR      NZ, M_1730
        INCW    R12
M_1730: LD      R8, #0
M_1732: CALL    @%5E
        INCW    R4
        ADD     R8, R14
        ADC     R7, R13
        ADC     R6, R12
        DECW    R10
        JR      PL, M_1732
        POP     RP
        POP     %51
        POP     %4F
        POP     %4E
        RET

        NOP

M_174A: PUSH    RP
        SRP     #%60
        LD      R0, #%F7
        LD      R1, #%A4
        LDE     R3, @RR0
        OR      R3, R3
        JR      NZ, M_1761
        CALL    M_14AE
        JR      C, M_1776
        LD      R3, #%80
        LD      R0, #%F7
M_1761: LD      R1, #%A5
        LD      R2, #%F5
        LD      R4, #%13
        LDEI    @R4, @RR2
        LDE     R4, @RR0
        LD      R1, #%A4
        CP      R3, R4
        JR      NZ, M_1773
        LD      R3, #0
M_1773: LDE     @RR0, R3
        RCF
M_1776: POP     RP
        RET

        NOP

M_177A: PUSH    RP
        SRP     #%20
        LD      %15, #1
        CALL    %FFF0       ; M_15D2
        JR      C, M_179E
        LD      R2, #0
        LD      R3, #0
M_178A: CALL    %FFF6       ; M_174A
        JR      C, M_1797
        LD      R4, #%13
        LDEI    @RR0, @R4
        INCW    %22
        JR      M_178A

M_1797: LD      R4, #0
        CP      %13, #%88
        JR      Z, M_17A0
M_179E: LD      R4, %13
M_17A0: CALL    %FFF3       ; M_15A8
        POP     RP
        RET

        LD      %2, #%30
        LD      P2M, #%0F
        PUSH    RP
        SRP     #%60
        LD      R0, #8
        LD      R1, %15
M_17B4: LD      R2, #0
        RRC     %61
        RRC     %62
        OR      %62, #%30
        LD      R4, %62
        LD      %2, R2
        LD      R3, #%18
M_17C3: DJNZ    R3, M_17C3
        OR      %62, #%40
        LD      %2, R2
        LD      R3, #%18
M_17CC: DJNZ    R3, M_17CC
        LD      %2, R4
        LD      R3, #%18
M_17D2: DJNZ    R3, M_17D2
        DJNZ    R0, M_17B4
M_17D6: LD      R0, #8
        AND     %60, %3
        JR      NZ, M_17D6
        LD      %2, #%10
        LD      R3, #%18
M_17E2: DJNZ    R3, M_17E2
        LD      %2, R4
        POP     RP
        RET

M_17E9: PUSH    %13
        CALL    M_081E
        LD      %5A, %13
        POP     %13
        RET

M_17F4: JP      M_17E9
M_17F7: JP      M_166A
M_17FA: JP      M_0F0A
M_17FD: JP      M_083A

        ; data read from M_1830
M_1800: .data %40 %41 %42 %44 %45 %46 %48 %49  %4a %4c %4d %4e %50 %51 %52 %54  %55 %56 %58 %59 %5a %5c %5d %5e
        .data %00 %50 %a8 %00 %50 %a8 %00 %50  %a8 %00 %50 %a8 %00 %50 %a8 %00  %50 %a8 %00 %50 %a8 %00 %50 %a8

        ; in: %5B = column
        ;     %5C = row
        ; out rr4 = address 18xx ?
        ; expects: RP=%60
M_1830: LD      R0, #%18
        LD      R1, %5C
        LDC     R4, @RR0
        ADD     R1, #ROWS
        LDC     R5, @RR0
        ADD     %65, %5B
        RET

        NOP

        ; nextVideoRamLineAddress
        ; in/out: rr4 address
M_1840: ADD     R5, #COLUMNS
        JR      C, M_184D
        JR      OV, M_184E
        TCM     R5, #%78
        JR      NZ, M_1854
        .data %0B             ; jr f = skip next byte
M_184D: INC     R4
M_184E: ADD     R5, #8
        ADC     R4, #0
M_1854: RET

        NOP

        ; getScreenCharAddress
        ; in: %5C = row
        ;     %5B = column
        ; out: rr0 = #%fc00 + 40 * %5c + %5b
M_1856: LD      R1, %5C
        RL      R1
        RL      R1
        RL      R1
        LD      R0, #0
        LD      R2, R1
        RCF
        RLC     R1
        RLC     R0
        RLC     R1
        RLC     R0
        ADD     R1, R2
        ADC     R0, #%FC
        ADD     %61, %5B
        ADC     R0, #0
        RET

        NOP

        ; in: %5A = char
        ;     %5B = column
        ;     %5C = row
        ;     %67 = char bitmap memory base address
M_1878: PUSH    RP
        SRP     #%60
        CALL    M_1856      ; getScreenCharAddress
        LD      R2, %5A
        LDE     @RR0, R2    ; store char at screen char address
        LD      R3, #0
        LD      R1, #5
M_1887: RCF                 ; r2 * 8 -> char bitmap memory address
        RRC     R2
        RRC     R3
        DJNZ    R1, M_1887
        ADD     R2, R7
        CALL    M_1830
        NOP                 ; ?
        LD      R0, #8      ; 8 bytes to video ram
        LD      R8, #%F7    ; #F7A0
        LD      R9, #%A0
        LDE     R9, @RR8
M_189C: LD      R8, #%60
        LD      P01M, #%B2  ; extended memory timing, P04-P07 = A12-A15, P10-P17 = AD0-AD7, P00-P03 = A8-A11
M_18A1: LDE     @RR8, R9    ; #60xx color?
        LDE     R1, @RR2    ; rr2=char map
        LDE     @RR4, R1    ; video ram
        COM     R1
        SWAP    R9
        LDE     @RR8, R9    ; #60xx?
        LDE     @RR4, R1
        SWAP    R9
        CALL    M_1840      ; nextVideoRamLineAddress
        INC     R3
        DJNZ    R0, M_18A1
        LD      P01M, #%92  ; back to normal memory timing
        POP     RP
        RET

        NOP

M_18BE: LD      R3, #%27
        SUB     %63, %5B
        TM      %55, #0b0100_0000
        JR      Z, M_18D0
        TM      %5C, #0b0000_0001
        JR      NZ, M_18D0
        ADD     %63, #%28
M_18D0: LD      %5F, R3
        INC     %5F
        JR      M_1856      ; getScreenCharAddress

        NOP
        NOP

        ; draw chars from screen char table
        ; print at screen
        ; in: %5B column
        ;     %5C row
        ;     %5Ew count
M_18D8: PUSH    %5B
        PUSH    %5C
M_18DC: CALL    M_1856      ; getScreenCharAddress
        LDE     R2, @RR0
        LD      %5A, R2
        CALL    M_1878
        INC     %5B
        CP      %5B, #COLUMNS
        JR      C, M_18F1
        INC     %5C         ; row++
        CLR     %5B         ; column = 0
M_18F1: DECW    %5E
        JR      NZ, M_18DC
        POP     %5C
        POP     %5B
        RET

M_18FA: TM      %55, #0b0010_0000
        JR      Z, M_1902
        CALL    %F512
M_1902: LD      %5A, %15
M_1905: PUSH    RP
        SRP     #%50
        OR      R5, R5          ; if %55 == 0xxx_xxx
        JR      PL, M_1922
        ; %55==1xxx_xxxx -> print (next) character (don't interpret < 0x10)
M_190D: AND     %55, #0b0111_1111
        CALL    M_1878
        INC     R11
        CP      %5B, #COLUMNS
        JR      C, M_191F
        INC     R12
        LD      R11, #0
        JP      M_1A9D

M_191F: POP     RP
        RET

M_1922: CP      %5A, #%10
        JR      NC, M_190D      ; %5A >= #%10?
        NOP
        DJNZ    R10, M_1938     ; r10=%5A
        DEC     %5B             ; case 1 (cursor left, Ctrl+A):
        JR      PL, M_191F
        LD      R11, #%27
M_1930: DEC     %5C
        JR      PL, M_191F
        LD      R12, #%17
        JR      M_191F

M_1938: DJNZ    R10, M_194C     ; case 2 (cursor right, Ctrl+B):
        INC     R11
        CP      %5B, #COLUMNS
        JR      C, M_191F       ; < 40
        LD      R11, #0
M_1942: INC     R12
        CP      %5C, #ROWS
        JR      C, M_191F
        LD      R12, #0
        JR      M_191F

M_194C: DJNZ    R10, M_1950
        JR      M_1930          ; case 3 (cursor up, Ctrl+C):

M_1950: DJNZ    R10, M_1954
        JR      M_1942          ; case 4 (cursor down, Ctrl+D):

M_1954: DJNZ    R10, M_195C
M_1956: LD      R11, #0         ; case 5 (cursor home, Ctrl+E):
        LD      R12, #0
        JR      M_191F

M_195C: DJNZ    R10, M_196A
        LD      R11, #0         ; case 6 (begin of line, Ctrl+F):
        TM      %55, #0b0100_0000 ; if %55==x1xx_xxxx, each line = 2 screen lines
        JR      Z, M_191F
        AND     %5C, #%FE
        JR      M_191F

M_196A: DJNZ    R10, M_198E
        SRP     #%60            ; case 7 (del, Ctrl+G):
        CALL    M_18BE
M_1971: DEC     %63
        NOP
        NOP
        JR      MI, M_1983
        INCW    %60
        LDE     R2, @RR0
        DECW    %60
        LDE     @RR0, R2
        INCW    %60
        JR      M_1971

M_1983: LD      R2, #%20
        LDE     @RR0, R2
        CLR     %5E
        CALL    M_18D8
M_198C: JR      M_191F

M_198E: DJNZ    R10, M_19AF
        OR      R11, R11        ; case 8 (backspace, Ctrl+H):
        JR      NZ, M_199E
        TM      %55, #%40
        JR      Z, M_191F
        TM      %5C, #1
        JR      Z, M_191F
M_199E: LD      R10, #1
        CALL    M_1905
        LD      R10, #%20
        CALL    M_1905
        LD      R10, #1
        CALL    M_1905
M_19AD: JR      M_198C

M_19AF: DJNZ    R10, M_19CA
        SRP     #%60            ; case 9 insert (Ctrl+I):
        CALL    M_18BE
        INC     R3
        LD      R2, #%20
M_19B9: LDE     R4, @RR0
        LDE     @RR0, R2
        LD      R2, %64
        INCW    %60
        DJNZ    R3, M_19B9
        CLR     %5E
        CALL    M_18D8
M_19C8: JR      M_19AD

M_19CA: DJNZ    R10, M_1A18
        LD      R10, #6         ; case 0x0A delete line? (Ctrl+J, Ctrl+M):
        CALL    M_1905
        SRP     #%60
        CALL    M_1856          ; getScreenCharAddress
        LD      R2, #0
        LD      R3, #%28
        TM      %55, #%40
        JR      Z, M_19E1
        LD      R3, #%50
M_19E1: LD      R9, %63
        LD      %5F, R3
        LD      R4, #%FF
        LD      R5, #%C0
        ADD     R3, R1
        ADC     R2, R0
        SUB     R5, R3
        SBC     R4, R2
        INCW    %64
        DECW    %64
        JR      Z, M_1A0B
        CLR     %5E
        ADD     %5F, %65
        ADC     %5E, %64
M_19FF: LDE     R8, @RR2
        LDE     @RR0, R8
        INCW    %62
        INCW    %60
        DECW    %64
        JR      NZ, M_19FF
M_1A0B: LD      R2, #%20
M_1A0D: LDE     @RR0, R2
        INCW    %60
        DJNZ    R9, M_1A0D
        CALL    M_18D8
M_1A16: JR      M_19C8

M_1A18: DJNZ    R10, M_1A6C
        NOP                     ; case 0x0B insert (double) line (Ctrl+K):
        LD      R10, #6
        CALL    M_1905
        SRP     #%60
        CALL    M_1856          ; getScreenCharAddress
        LD      R2, #%FF
        LD      R3, #%BF
        LD      R9, #%28
        TM      %55, #%40
        JR      Z, M_1A32
        LD      R9, #%50
M_1A32: SUB     R3, R9
        LD      %5E, #%FF
        LD      %5F, #%C0
        SUB     %5F, %61
        SBC     %5E, %60
        LD      R4, %5E
        LD      R5, %5F
        SUB     R5, R9
        SBC     %64, #0
        INCW    %64
        DECW    %64
        JR      Z, M_1A5F
        LD      R0, #%FF
        LD      R1, #%BF
M_1A53: LDE     R8, @RR2
        LDE     @RR0, R8
        DECW    %60
        DECW    %62
        DECW    %64
        JR      NZ, M_1A53
M_1A5F: LD      R2, #%20
M_1A61: LDE     @RR0, R2
        DECW    %60
        DJNZ    R9, M_1A61
M_1A67: CALL    M_18D8          ; redraw screen
        JR      M_1A16

M_1A6C: DJNZ    R10, M_1A8C
        LD      R9, #%20        ; case 0x0C clear screen (Ctrl+L):
        LD      R14, #%FC       ; fill screenCharBuffer with 0x20 {
        LD      R15, #0
        LD      R10, #3         ; %3C0=40*24
        LD      R11, #%C0
M_1A78: LDE     @RR14, R9
        INCW    %5E
        DECW    %5A
        JR      NZ, M_1A78      ; }
        LD      R14, #3
        LD      R15, #%C0
        SRP     #%60
        CLR     %5C
        JR      M_1A67

M_1A8A: JR      M_1A16

M_1A8C: DJNZ    R10, M_1AE5
        LD      R10, #6         ; case 0x0D
        CALL    M_1905
        INC     R12
        TM      %55, #%40
        JR      Z, M_1A9D
        INC     R12
        AND     %5C, #%FE
M_1A9D: CP      %5C, #ROWS
        JR      C, M_1A8A
        TM      %55, #%10
        JP      Z, M_1956
        SRP     #%60
        LD      R0, #%FC
        LD      R1, #0
        LD      R2, #%FC
        LD      R3, #%A0
        NOP
        LD      R4, #3
        LD      R5, #%C0
        LD      %5E, R4
        LD      %5F, R5
        SUB     R5, R3
        LD      R9, %63
        NOP
M_1AC0: LDE     R8, @RR2
        LDE     @RR0, R8
        INCW    %60
        INCW    %62
        DECW    %64
        JR      NZ, M_1AC0
        LD      R8, #%20
M_1ACE: LDE     @RR0, R8
        INCW    %60
        DJNZ    R9, M_1ACE
        CLR     %5C
        CALL    M_18D8
        LD      %5C, #%14
        .repeat 7
            NOP
        .end
        JR      M_1A8A

M_1AE5: DJNZ    R10, M_1AEA
        OR      %55, #%80           ; case 0x0E print control character (Ctrl+N):
M_1AEA: JR      M_1A8A
        .repeat 4
            NOP
        .end

        ; short beep
M_1AF0: PUSH    RP
        SRP     #%60
        PUSH    R1
        PUSH    R0
        LD      R1, #%10
M_1AFA: XOR     %3, #%40    ; toggle P36
        LD      R0, #%60
M_1AFF: DJNZ    R0, M_1AFF  ; wait ~1.152ms
        DJNZ    R1, M_1AFA  ; toggle P35 16x
        POP     R0
        POP     R1
        POP     RP
        RET

        ; read key
M_1B0A: PUSH    RP
        SRP     #%60
        LD      R13, %55
        SWAP    %6D
        AND     %6D, #%80
        LD      R0, #0
        LD      R2, #0
        LD      R5, #%0F
M_1B1B: LD      R4, #%7F    ; read keyboard matrix from 7f00
        LDC     R3, @RR4
        PUSH    R5
        LD      R1, #8      ; line numbers
M_1B23: RLC     R3
        JR      NC, M_1B37
        LD      R4, #%1D    ; key pressed
        LDE     R8, @RR4    ; get keycode from table
        CP      R8, #%B0
        JR      NC, M_1B35  ; >=
        LD      R2, #%80
        OR      R2, R5
        .data %0d         ; jp f = skip next 2 bytes
M_1B35: LD      R0, R8
M_1B37: ADD     R5, #%10
        DJNZ    R1, M_1B23
        NOP
        POP     R5
        DEC     R5
        JR      PL, M_1B1B  ; >= 0? next column
        XOR     R2, #%80
        JR      PL, M_1B4D
M_1B48: LD      R13, #0
M_1B4A: POP     RP
        RET

M_1B4D: LD      R1, #0
        RRC     R0
        RRC     R1
        RRC     R0
        RRC     R1
        AND     R0, #%0F
        ADD     R0, #%1D
        OR      R1, R2
        LDE     R3, @RR0    ; get keycode from table
        OR      R3, R3
        JR      MI, M_1B7B
        TM      R12, #%20   ; %6C == xx1x_xxxx -> automatic to uppercase
        JR      Z, M_1B77
        ; to uppercase
        CP      R3, #%61
        JR      C, M_1B77
        CP      R3, #%7B
        JR      NC, M_1B77
        SUB     R3, #%20
M_1B77: OR      R13, R3
        JR      M_1B4A

M_1B7B: RCF
        RLC     R3
        RCF
        RLC     R3
        OR      R3, #%E0
        LD      R2, #%F7
        CALL    @%62
        SRP     #%60
        LD      R0, #%30
M_1B8C: CALL    M_1AF0
        DJNZ    R0, M_1B8C
        JR      M_1B48

        NOP

M_1B94: PUSH    RP
        SRP     #%60
        LD      R0, #%F7    ; lde r2, %F7A0
        LD      R1, #%A0
        LDE     R2, @RR0
        PUSH    %62
        INC     R1
        LDE     R2, @RR0
        DEC     %61
        LDE     @RR0, R2
        CLR     %5E
        LD      %5F, #1
        CALL    M_18D8
        POP     %62
        LD      R0, #%F7
        LD      R1, #%A0
        LDE     @RR0, R2
        JR      M_1BBC

M_1BB9: AND     %6C, #%7F   ; 0xxx_xxxx
M_1BBC: LD      %5F, R13
        CALL    M_081B      ; get key
        LD      %13, R13
        OR      R13, R13
        JR      Z, M_1BB9   ; no key?
        LD      R0, #%1F
        AND     R0, R12
        LD      R15, %60
M_1BCD: OR      R15, R15    ; wait until %5F is cleared
        JR      NZ, M_1BCD
        CP      %5F, %6D
        JR      Z, M_1BDB
        AND     %6C, #%7F
        JR      M_1BF4

M_1BDB: OR      R12, R12
        JR      MI, M_1BF4
        LD      R15, #%80
M_1BE1: CALL    M_081B
        OR      R13, R13
        JR      Z, M_1BB9
        CP      %5F, %6D
        JR      NZ, M_1BB9
        OR      R15, R15
        JR      NZ, M_1BE1
        OR      %6C, #%80
M_1BF4: TM      %6C, #%40   ; %6C == x1xx_xxxx -> beep after keypress
        JR      Z, M_1BFC
        CALL    M_1AF0      ; beep
M_1BFC: CLR     %5E
        LD      %5F, #1
        CALL    M_18D8
        POP     RP
        RET

        .repeat 7
            NOP
        .end

        ; interrupt
M_1C0E: OR      %6F, %6F
        JR      Z, M_1C15
        DEC     %6F
M_1C15: IRET

        .repeat 3
            NOP
        .end

M_1C19: PUSH    RP
        SRP     #%50
        OR      R8, R8
        JR      MI, M_1C3D
M_1C21: DEC     %58
        JR      PL, M_1C2C
        LD      R10, #%0D
M_1C27: LD      %13, R10
        POP     RP
        RET

M_1C2C: LD      R14, #%F7   ; { ld r10, %F7A2
        LD      R15, #%A2
        LDE     R10, @RR14  ; }
        PUSH    %5A
        INC     R10
        LDE     @RR14, R10
        POP     %5F
        LDE     R10, @RR14
        JR      M_1C27

M_1C3D: CALL    M_081E
        LD      R10, %13
        CALL    M_1905
        TM      %55, #%80
        JR      NZ, M_1C3D
        CP      %13, #%0D
        JR      NZ, M_1C3D
        SRP     #%60
        CALL    M_1856              ; getScreenCharAddress
        LD      R2, #%28
        TM      %55, #%40
        JR      Z, M_1C5D
        LD      R2, #%50
M_1C5D: SUB     R1, R2
        SBC     R0, #0
        LD      R4, #%F7
        LD      R5, #0
        CLR     %58
M_1C68: LDE     R3, @RR0
        LDE     @RR4, R3
        CP      R3, #%20
        JR      Z, M_1C75
        LD      %58, R5
        INC     %58
M_1C75: INCW    %60
        INC     R5
        DJNZ    R2, M_1C68
        SRP     #%50
        LD      R14, #%F7
        LD      R15, #%A2
        LD      R10, #0
        LDE     @RR14, R10
        JR      M_1C21
M_1C86: PUSH    RP
        SRP     #%20
        LD      %15, #2
        CALL    %FFF0                   ; M_15D2
        JR      C, M_1CA2
M_1C92: LD      R4, #%15
        LDEI    @R4, @RR0
        CALL    %FFF9                   ; M_1614
        JR      C, M_1CA2
        DECW    %22
        JR      NZ, M_1C92
        LD      R4, #0
        .data %0d                         ; jp f = skip next 2 bytes
M_1CA2: LD      R4, %13
        CALL    %FFF3       ; M_15A8
        JR      NC, M_1CAB
        LD      R4, %13
M_1CAB: POP     RP
        RET

M_1CAE: PUSH    RP
        SRP     #%70
        LD      R0, #%F7
        LD      R1, #%A8
        LD      R2, #%74
        LD      R3, #4
M_1CBA: LDEI    @R2, @RR0
        DJNZ    R3, M_1CBA
        LD      R3, #3
        AND     R3, R7
        ADD     R5, R7
        ADC     R4, R6
        INCW    R6
        DJNZ    R3, M_1CD3
        RCF
        TM      R4, #%10
        JR      Z, M_1CD1
        SCF
M_1CD1: JR      M_1CEF
M_1CD3: DJNZ    R3, M_1CDE
        SCF
        TM      R4, #%80
        JR      Z, M_1CD1
        RCF
        JR      M_1CD1
M_1CDE: DJNZ    R3, M_1CE9
        RCF
        TM      R5, #4
        JR      Z, M_1CD1
        SCF
        JR      M_1CD1
M_1CE9: SCF
        OR      R5, R5
        JR      PL, M_1CEF
        RCF
M_1CEF: RLC     %74
        RLC     %75
        LD      R2, #%74
        LD      R1, #%A8
        LD      R3, #4
M_1CF9: LDEI    @RR0, @R2
        DJNZ    R3, M_1CF9
        POP     RP
        RET

        ; keyboard mapping
        ; read from M_1B0A
M_1D00:
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "xvn,;" %0E ' ' %FF
		.data %FF %FF %FF %FF  %FF %FF %C2 %FF  "sfhk:#\r" %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "wrzip" %02 ']' %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "24680" %03 '[' %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "ycbm." %08 %07 %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "adgjl" %04 %05 %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "qetuo" %01 %FF %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "13579-^" %FF

		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "XVN<?" %0E ' ' %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "SFHK*|\r" %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "WRZIP" %02 '}' %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "\"$&(=" %03 '{' %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "YCBM>" %FF %07 %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "ADGJL" %04 %05 %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "QETUO" %01 %FF %FF
		.data %FF %FF %FF %FF  %FF %FF %FF %FF  "!@%/)+\\" %FF

        .repeat 8
            .repeat 16
                .data %FF
            .end
        .end
/*
        .data %FF %FF "1234567890<" %FF %FF %FF
        .data %FF %C3 "qwertyuiop+" %FF %FF %FF
        .data %FF %C2 "asdfghjkl;*" %FF %FF %FF
        .data %FF %C1 "zxcvbnm,. \r" %FF %FF %FF

        .data %FF %FF "!\"#$%&'@()>" %FF %FF %FF
        .data %FF %FF "QWERTYUIOP-" %FF %FF %FF
        .data %FF %FF "ASDFGHJKL:/" %FF %FF %FF
        .data %FF %FF "ZXCVBNM[]=?" %FF %FF %FF

        .data %FF %FF %08 %09 %0C %FF %1A %1B %1C %1D %1E %1F %5E %FF %FF %FF
        .data %FF %FF %07 %03 %06 %0A %FF %FF %FF %FF %FF %FF %5C %FF %FF %FF
        .data %FF %FF %01 %05 %02 %0B %FF %FF %FF %FF %FF %FF %7C %FF %FF %FF
        .data %FF %FF %0F %04 %FF %0E %FF %FF %FF %7B %7D %5F %7E %FF %FF %FF

        .data %FF %FF %11 %12 %13 %14 %15 %16 %17 %18 %19 %10 %7F %FF %FF %FF
        .data %FF %FF %80 %81 %82 %83 %84 %85 %86 %87 %FF %FF %60 %FF %FF %FF
        .data %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF
        .data %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF %FF

        .repeat 4
            .repeat 16
                .data %FF
            .end
        .end

        .repeat 4
            .repeat 16
                .data %FF
            .end
        .end
*/

M_1E80: PUSH    RP
        SRP     #%60
        LD      R0, #%FC
        LD      R1, #0
        LD      R2, #3
        LD      R3, #%C0
        LD      %5E, R2
        LD      %5F, R3
M_1E90: LDE     R4, @RR0
        SUB     R0, #4
        LDE     R5, @RR0
        LDE     @RR0, R4
        ADD     R0, #4
        LDE     @RR0, R5
        INCW    R0
        DECW    R2
        JR      NZ, M_1E90
        CLR     %5B
        CLR     %5C
        CALL    M_18D8
        POP     RP
        RET

        NOP
        NOP
        XOR     %6C, #%40
        RET

        XOR     %6C, #%20
        RET

        OR      %55, #%20
        RET

        AND     %55, #%DF
        RET

        JP      M_1E80

        RET
        XOR     %55, #8
        RET

        RET
        RET
        RET
        RET
        RET
        RET
        RET
        RET

        ; %
M_1ED0: CALL    M_0CA9      ; getHexWordFromRR14_forgetCallerIfError
        LD      R5, #%23
        CALL    M_0818      ; PTC
        LD      R12, #0
        LD      R8, #0
        LD      R9, #0
        LD      R13, #%10
M_1EE0: RLC     %1B
        RLC     %1A
        ADC     R9, R9
        DA      %19
        ADC     R8, R8
        DA      %18
        ADC     R12, R12
        DJNZ    R13, M_1EE0
        LD      R5, #%30
        ADD     R5, R12
        CALL    M_0818      ; PTC
        CALL    M_0C69      ; printHexWord
        JP      M_0C8D      ; println

        NOP
        NOP
        NOP

M_1F00: PUSH    RP
        SRP     #%60
        CALL    M_0FBB
        LD      R4, %60
        LD      R5, %61
        SRP     #%70
        LD      R0, #%60
        LD      R1, %4B
        COM     %71
        SWAP    %71
        LDE     @RR0, R1
        LD      R0, %4C
        LD      R1, %4D
        LD      R2, %52
        LD      R3, %53
        LD      R12, #%10
M_1F21: LD      R4, %63
        LD      R5, #%76
        LDEI    @R5, @RR0
        LDEI    @R5, @RR0
        LDEI    @R5, @RR0
M_1F2B: RL      R4
        JR      C, M_1F37
        RRC     %76
        RRC     %77
        RRC     %78
        JR      M_1F2B

M_1F37: LD      R10, %64
        LD      R11, %65
        LD      R5, #%76
        LD      R4, #3
        LD      P01M, #%B2
M_1F42: TM      %3, #4
        JR      NZ, M_1F42
M_1F47: LDE     R9, @RR10
        LDE     @RR2, R9
        XOR     R9, @R5
        TM      %4A, #1
        JR      Z, M_1F54
        OR      R9, @R5
M_1F54: LDE     @RR10, R9
        INC     R5
        INCW    R2
        INCW    R10
        DJNZ    R4, M_1F47
        LD      P01M, #%92
        SRP     #%60
        CALL    M_1840      ; nextVideoRamLineAddress
        SRP     #%70
        DJNZ    R12, M_1F21
        POP     RP
        RET

        NOP

M_1F6D: PUSH    RP
        SRP     #%60
        CALL    M_0FBB
        LD      R2, #%60
        LD      R3, %4B
        COM     %63
        SWAP    %63
        LDE     @RR2, R3
        LD      R2, %52
        LD      R3, %53
        LD      R9, #%10
        LD      R4, %60
        LD      R5, %61
M_1F88: LD      R8, #3
        LD      P01M, #%B2
M_1F8D: TM      %3, #4
        JR      NZ, M_1F8D
M_1F92: LDE     R6, @RR2
        LDE     @RR0, R6
        INCW    R0
        INCW    R2
        DJNZ    R8, M_1F92
        LD      P01M, #%92
        CALL    M_1840      ; nextVideoRamLineAddress
        LD      R0, %64
        LD      R1, %65
        DJNZ    R9, M_1F88
        POP     RP
        RET

        .repeat 53
            NOP
        .end

        ; copied to FFE0..FFFF
M_1FE0: NOP
        NOP
        NOP
        NOP
        RCF
        RET

        ; GTC
M_1FE6: ; = FFE6
        JP      M_0A35

        ; PTC
M_1FE9: ; = FFE9
        JP      M_0A30

M_1FEC: RCF
        NOP
        RET

        NOP
M_1FF0: ; = FFF0
        JP      M_15D2
M_1FF3: ; = FFF3
        JP      M_15A8
M_1FF6: ; = FFF6
        JP      M_174A
M_1FF9: ; = FFF9
        JP      M_1614

M_1FFC: RCF
        NOP
        RET
        NOP
