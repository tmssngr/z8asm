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

        .ORG    %8000

M_8000: CALL    %082D
        .data   %0c
        .data   "   MM     MM IIII NN    NN EEEEEEE" %0d
        .data   "   MMM   MMM  II  NNN   NN  EE   E" %0d
        .data   "   MMMM MMMM  II  NNNN  NN  EE"     %0d
        .data   "   MM MMM MM  II  NN NN NN  EEEE"   %0d
        .data   "   MM  M  MM  II  NN  NNNN  EE"     %0d
        .data   "   MM     MM  II  NN   NNN  EE"     %0d
        .data   "   MM     MM  II  NN    NN  EE   E" %0d
        .data   "   MM     MM IIII NN    NN EEEEEEE" %0d
        .data   %0d %0d
        .data   " Record Holders:" %0d %0d %0d %0d
        .data   "   I...Instructions" %0d %0d
        .data   "   P...Parameter" %0d %0d
        .data   "   G...Game-begin" %0d %0d
        .data   "   E...End" %0d %00
        LD      %50, #%0A
        LD      %51, #%12
        CALL    M_819C
        SRP     #%60
M_8175: OR      %6C, #%20
        CALL    %081B
        OR      R13, R13
        JR      Z, M_8175
        CP      R13, #'I'
        JR      Z, M_8AA0
        CP      R13, #'P'
        JR      Z, M_88D0
        CP      R13, #'G'
        JR      Z, M_8380
        CP      R13, #'E'
        JR      NZ, M_8175
        CALL    %082D
        LD      R0, #0
        RET

        ; Print record holders
M_819C: SRP     #%10
        LD      R0, #M_81D0
        LD      R1, #M_81D0
        LD      R2, %51
        LD      %5C, %50
        LD      R4, #3
M_81A9: LD      R6, #%0E
        LD      %5B, R2
M_81AD: LDE     R5, @RR0
        INCW    R0
        CALL    %0818
        DJNZ    R6, M_81AD
        LD      R5, #%20
        CALL    %0818
        PUSH    R4
        PUSH    R2
        LD      R5, #%12
        LDEI    @R5, @RR0
        LDEI    @R5, @RR0
        CALL    %06E5
        POP     R2
        POP     R4
        DJNZ    R4, M_81A9
        RET
        .align  %10, %ff
M_81D0: .data   "Thomas Singer "
M_81DE: .data   %06 %3c
        .data   "Thomas Singer " %00 %51
        .data   "Thomas Singer " %00 %18

M_8200: .data   %AA %55 %AA %55 %AA %55 %AA %55
        .data   %10 %28 %44 %28 %10 %38 %7c %fe
        .data   %00 %60 %60 %00 %00 %06 %06 %00

M_8218: .data   %00 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %01 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %01 %01  %00 %01 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %01  %00 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %01 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %01 %00 %00
        .data   %00 %00 %01 %00 %00 %01 %00 %00  %00 %00 %00 %01 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %01 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %01 %00  %00 %01 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %01 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %00  %00 %01 %01 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %01  %00 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %01 %00 %00  %00 %00 %00 %00 %00 %00 %00 %01
        .data   %00 %01 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %00  %01 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data   %01 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %01 %00 %00 %01 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %00  %01 %00 %00 %00 %00 %00 %00 %00
        .data   %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00

M_8380: SRP     #%50
        CALL    %082D
        .data   %0c
        .data   " M   M I N   N EEEE"          %0d
        .data   " MM MM I NN  N E     Record:" %0d
        .data   " M M M I N N N EEE"           %0d
        .data   " M   M I N  NN E     Steps :" %0d
        .data   " M   M I N   N E"             %0d
        .data   " M   M I N   N EEEE  Level :" %0d %0d %00
        LD      R11, #%15
        CALL    %082D
        .data   "Ladder:" %0d %0d %00

        LD      R11, #%15
        CALL    %082D
        .data   "Mines" %0d %00

        LD      R11, #%15
        CALL    %082D
        .data   "around you:" %0d %0d %0d %00

        LD      R11, #%15
        CALL    %082D
        .data   "Use cursor keys:" %0d %0d %00

        LD      R11, #%18
        CALL    %082D
        .data   %0e %03 %0d %00

        LD      R11, #%16
        CALL    %082D
        .data   %0e %01 "-*-" %0e %02 %0d %00

        LD      R11, #%18
        CALL    %082D
        .data   %0e %04 %0d %0d %00

        .repeat %0e
          NOP
        .end

        LD      R11, #%14
        CALL    %082D
        .data   "Copyright in Chem-" %0d %00

        LD      R11, #%14
        CALL    %082D
        .data   "nitz by SINGER-SOFT" %0d %00

        LD      R11, #%16
        CALL    %082D
        .data   "January '91" %00

        SRP     #%10
        LD      R0, #M_81DE
        LD      R1, #M_81DE
        LDE     R2, @RR0
        INC     R1
        LDE     R3, @RR0
        NOP
        LD      %5B, #%1C
        LD      %5C, #1
        CALL    %0EE0
        LD      %5B, #%1C
        LD      %5C, #5
        LD      %5A, #%34
        CALL    %1878
        SRP     #%20
        LD      R15, #3
        CLR     %30
        LD      R2, #0
        LD      R3, #0
        NOP
        NOP
        NOP
        NOP
M_8500: SRP     #%20
        LD      R4, #M_8218
        LD      R5, #M_8218
        LD      R6, #1
        LD      R7, #%68
        LD      R8, #0
M_850C: LDE     @RR4, R8
        INCW    R4
        DECW    R6
        JR      NZ, M_850C
        LD      R9, #%38
M_8516: CALL    %0836
        LD      R4, %74
        LD      R5, %75
        SWAP    R5
        AND     R4, #%0F
        AND     R5, #%0F
        INC     R4
        INC     R5
        INC     R5
        CALL    M_8534
        LD      R4, #1
        LDE     @RR6, R4
        DJNZ    R9, M_8516
        NOP
        JR      M_854C
M_8534: LD      R6, #M_8218
        LD      R7, #M_8218
        LD      R8, R4
        OR      R8, R8
        JR      Z, M_8546
M_853E: ADD     R7, #%14
        ADC     R6, #0
        DJNZ    R8, M_853E
M_8546: ADD     R7, R5
        ADC     R6, #0
        RET

M_854C: SRP     #%50
        LD      %67, #%80
        LD      R10, #%40
        LD      R12, #7
M_8555: LD      R11, #1
M_8557: CALL    %1878
        INC     R11
        CP      %5B, #%13
        JR      C, M_8557
        INC     R12
        CP      %5C, #%17
        JR      C, M_8555
        LD      %67, #%10
        LD      R10, #%20
        NOP
        NOP
        NOP
        NOP
        LD      R11, #%12
        LD      R12, #%0E
        CALL    %1878
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
M_8580: SRP     #%20
        LD      R0, #8
        LD      R1, #1
        LD      %5B, #%1C
        LD      %5C, #7
        LD      %5A, R15
        OR      %5A, #%30
        CALL    %1878
        NOP
        NOP
        NOP
        NOP
M_8598: LD      %67, #%80
        LD      %5A, #%41
        LD      R4, R0
        LD      R5, R1
        CALL    M_85A8
        NOP
        JR      M_85B8
M_85A8: CLR     %5B
        LD      %5C, #6
        ADD     %5C, %24
        ADD     %5B, %25
        JR      %1878
        NOP
        NOP
M_85B8: LD      %67, #%10
        SRP     #%10
        LD      R2, %22
        LD      R3, %23
        LD      %5B, #%1C
        LD      %5C, #3
        CALL    %0EE0
        NOP
        SRP     #%20
        LD      R4, R0
        LD      R5, R1
        LD      R14, #0
        DEC     R5
        CALL    M_85EB
        INC     R5
        INC     R5
        CALL    M_85EB
        DEC     R5
        DEC     R4
        CALL    M_85EB
        INC     R4
        INC     R4
        CALL    M_85EB
        JR      M_85FB
M_85EB: CALL    M_8534
        LDE     R13, @RR6
        OR      R13, R13
        JR      Z, M_85F5
        INC     R14
M_85F5: RET
        NOP
        NOP
        NOP
        NOP
        NOP
M_85FB: LD      R4, #%86
        LD      R5, #%80
        LD      %5C, #5
        OR      R14, R14
        JR      Z, M_860E
M_8606: ADD     R5, #%24
        ADC     R4, #0
        DJNZ    R14, M_8606
M_860E: LD      R6, #6
M_8610: LD      %5B, #%20
        LD      R7, #6
M_8615: LD      R8, #%15
        LDEI    @R8, @RR4
        CALL    %0818
        DJNZ    R7, M_8615
        INC     %5C
        DJNZ    R6, M_8610
        NOP
        CALL    M_8628
        JR      M_8639
M_8628: CALL    %081B
        OR      %6D, %6D
        JR      NZ, M_8628
M_8630: CALL    %081B
        OR      %6D, %6D
        JR      Z, M_8630
        RET
M_8639: LD      %67, #%80
        LD      %5A, #%42
        LD      R4, R0
        LD      R5, R1
        CALL    M_85A8
        LD      %67, #%10
        SRP     #%20
        CP      %6D, #1
        JR      NZ, M_8657
        CP      R1, #2
        JR      C, M_8657
        DEC     R1
M_8657: CP      %6D, #2
        JR      NZ, M_8662
        CP      R1, #%12
        JR      NC, M_8662
        INC     R1
M_8662: CP      %6D, #3
        JR      NZ, M_866E
        CP      R0, #2
        JR      C, M_866E
        DEC     R0
M_866E: CP      %6D, #4
        JR      NZ, M_8679
        CP      R0, #%10
        JR      NC, M_8679
        INC     R0
M_8679: JR      M_8710
        NOP
        NOP
        NOP
        NOP

M_8680: .data   " 0000 "
        .data   "0   00"
        .data   "0  0 0"
        .data   "0 0  0"
        .data   "00   0"
        .data   " 0000 "
        .data   "   1  "
        .data   "  11  "
        .data   " 1 1  "
        .data   "   1  "
        .data   "   1  "
        .data   "  111 "
        .data   " 2222 "
        .data   "2    2"
        .data   "   22 "
        .data   " 22   "
        .data   "2     "
        .data   "222222"
        .data   "33333 "
        .data   "     3"
        .data   "  333 "
        .data   "     3"
        .data   "3    3"
        .data   " 3333 "

M_8710: CALL    M_85EB
        OR      R13, R13
        JR      Z, M_873C
        LD      %5A, #%2A
        CALL    M_85A8
        LD      R4, #4
        LD      PRE1, #%23
        LD      TMR, #%8A
M_8725: LD      T1, R4
        LD      R5, #5
M_8729: DJNZ    R6, M_8729
        DJNZ    R5, M_8729
        INC     R4
        JR      NZ, M_8725
        LD      TMR, #3
        DEC     %2F
        JR      NZ, M_8580
        JR      M_8756
        NOP
        NOP
M_873C: CP      %21, #%12
        JR      NZ, M_8750
        CP      %20, #8
        JR      NZ, M_8750
        INC     %30
        CALL    M_88B0
        NOP
        NOP
        JR      M_8500
M_8750: INCW    %22
        JR      M_8598
        NOP
M_8756: SRP     #%20
        LD      R0, #%84
        LD      R1, #%EE
        LDE     R4, @RR0
        AND     R4, #7
        LD      R10, #0
        JR      Z, M_876A
M_8765: ADD     %2A, %30
        DJNZ    R4, M_8765
M_876A: NOP
        NOP
        LD      R11, R3
        ADD     R10, R2
        CALL    %082D
        .data   %0c %0d "You had collect " %00

        SRP     #%10
        LD      R2, %2A
        LD      R3, %2B
        CALL    %0EE0
        CALL    %082D
        .data   " Points." %0d %0d %00

        SRP     #%20
        LD      R0, #%81
        LD      R1, #%DE
        LD      R2, #3
M_87A5: LDE     R4, @RR0
        INC     R1
        LDE     R5, @RR0
        DEC     R1
        CP      R10, R4
        JR      C, M_8831
        JR      UGT, M_87B8
        CP      R11, R5
        JP      ULE, M_8831         ; could be JR
M_87B8: CALL    %082D
        .data   " Record Holders :" %0d
        .data   "---------------" %0d %00

        PUSH    R2
        CALL    M_8888
        LD      %50, #6
        CLR     %51
        CALL    M_819C
        POP     R2
        CALL    M_887A
        SUB     %21, #%0E
        NOP
        SRP     #%10
        LD      R2, %2A
        LD      R3, %2B
        CALL    %0182
        LD      R10, #6
        LD      R11, #%14
M_8801: PUSH    R5
        LD      R5, @R11
        INC     R11
        CALL    %0818
        POP     R5
        DJNZ    R10, M_8801
        CLR     %5B
        LD      %55, #%14
        LD      %58, #%FF
        CALL    %0815
        SRP     #%20
        LD      R2, #%F7
        LD      R3, #0
        LD      R4, #%0E
M_8820: LDE     R5, @RR2
        LDE     @RR0, R5
        INC     R1
        INC     R3
        DJNZ    R4, M_8820
        LD      R2, #%2A
        LDEI    @RR0, @R2
        LDEI    @RR0, @R2
        JR      M_8000
M_8831: ADD     R1, #%10
        DEC     R2
        JR      NZ, M_87A5
        CALL    %082D
        .data   %0d %0d "So you can't come in the list of the" %0d
        .data   "Record Holders." %00

        CALL    M_8628
        JR      M_8000
        NOP
M_887A: LD      %5C, #9
        SUB     %5C, R2
        LD      %5B, #%0F
        RET
        NOP
        NOP
        NOP
        NOP
M_8888: CP      R2, #1
        JR      NZ, M_888E
        RET
M_888E: LD      R3, #%10
        LD      R4, #%81
        LD      R5, #%EF
        LD      R6, #%81
        LD      R7, #%FF
        CP      R2, #3
        JR      NZ, M_889F
        LD      R3, #%20
M_889F: LDE     R8, @RR4
        LDE     @RR6, R8
        DEC     R5
        DEC     R7
        DJNZ    R3, M_889F
        RET
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
M_88B0: LD      R4, #0
        LD      R5, #%E0
        CALL    M_88C2
        LD      R4, #0
        LD      R5, #%A0
        CALL    M_88C2
        LD      R4, #0
        LD      R5, #%D0
M_88C2: LD      R7, R5
        XOR     %3, #%40
M_88C7: DJNZ    R7, M_88C7
M_88C9: DJNZ    R7, M_88C9
        DJNZ    R4, M_88C2
M_88CD: DJNZ    R5, M_88CD
        RET
M_88D0: SRP     #%50
        LD      R11, #0
        LD      R12, #%0A
        CALL    %082D
        .data   "Press :- " %22 %00

        OR      %55, #%80
        NOP
        CALL    %082D
        .data   %0d %22 " if you had select the level"
        .data   "                                               - "
        .data   %22 "+" %22 " if you want to increase the             level"
        .data   "                "
        .data   "                "
        .data   "                "
        .data   "                "
        .data   "     - "
        .data   %22 "-" %22 " if you want to decrease the             level"
        .data   "                "
        .data   "                "
        .data   "                "
        .data   "              Level:           " %0d %0d
        .data   "                            " %00

        SRP     #%20
M_8A53: LD      R0, #%84
        LD      R1, #%EE
M_8A57: LDE     R2, @RR0
        LD      %15, R2
        LD      %5C, #%12
        LD      %5B, #7
        CALL    %0818
M_8A64: CALL    M_8628
        CP      %6D, #%0D
        JR      Z, M_8000
        CP      %6D, #%2B
        JR      NZ, M_8A88
        CP      R2, #%36
        JR      NC, M_8A57
        INC     R2
        LDE     @RR0, R2
        LD      R0, #%85
        LD      R1, #%15
        LDE     R2, @RR0
        ADD     R2, #8
M_8A83: LDE     @RR0, R2
        JR      M_8A53

        NOP

M_8A88: CP      %6D, #%2D
        JR      NZ, M_8A64
        CP      R2, #%31
        JR      C, M_8A64
        DEC     R2
        LDE     @RR0, R2
        INC     R0
        LD      R1, #%15
        LDE     R2, @RR0
        SUB     R2, #8
        JR      M_8A83

        ; Instructions
M_8AA0: CLR     %5B
        LD      %5C, #%0A
        CALL    %082D
        .data   "MINE is a strategic game. You must cross"
        .data   "mine-fields from left to right. The big "
        .data   "digit, which you can see right of the   "
        .data   "picture screen, shows the number of the "
        .data   "mines around you. The left and the right"
        .data   "border are free. You have at hand three "
        .data   "ladders. If you crossed a mine-field the"
        .data   "next appears." %0d
        .data   "                         " %0d
        .data   "Points := 256 * Level * Fields + Steps  "
        .data   "                            " %0d
        .data   "                          Press any key    " %00

        CALL    M_8628
        JR      M_8000
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
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        .data   "  " %0d %0d "                            " %00
M_8CA1: SRP     #%20
        LD      R0, #%85
        LD      R1, #%E5
        LDE     R2, @RR0
        LD      %15, R2
        LD      %5C, #%12
        LD      %5B, #7
        CALL    %0818
M_8CB4: CALL    %081B
        OR      %6D, %6D
        JR      NZ, M_8CB4
M_8CBC: CALL    %081B
        OR      %6D, %6D
        JR      Z, M_8CBC
        CP      %6D, #%0D
        JR      Z, M_8000
        CP      %6D, #%2B
        JR      NZ, M_8D08
        CP      R2, #%34
        JR      NC, M_8CB4
        INC     R2
        LDE     @RR0, R2
        CALL    %082D
        DEC     @%0
        LD      %15, R2
        CALL    %0818
        LD      R0, #%86
        LD      R1, #%B4
        LDE     R2, @RR0
        SUB     R2, #2
        LDE     @RR0, R2
        LD      R1, #%6A
        RCF
        RRC     R2
        LDE     @RR0, R2
        LD      R0, #%88
        LD      R1, #%71
        LDE     R2, @RR0
        ADD     R2, #3
        LDE     @RR0, R2
        JP      M_8CA1       ; could be JR
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
        NOP
M_8D08: CP      R2, #%31
        JP      C, M_8CBC    ; could be JR
        DEC     R2
        LDE     @RR0, R2
        CALL    %082D
        DEC     @%0
        LD      %15, R2
        CALL    %0818
        LD      R0, #%86
        LD      R1, #%B4
        LDE     R2, @RR0
        ADD     R2, #2
        LDE     @RR0, R2
        LD      R1, #%6A
        RCF
        RRC     R2
        LDE     @RR0, R2
        LD      R0, #%88
        LD      R1, #%71
        LDE     R2, @RR0
        SUB     R2, #3
        LDE     @RR0, R2
        JR      M_8CA1
        NOP
        NOP
        NOP
        NOP
        CP      R2, #1
        JR      NZ, M_8D46
        RET
M_8D46: LD      R3, #%10
        LD      R4, #%81
        LD      R5, #%D3
        LD      R6, #%81
        LD      R7, #%E3
        CP      R2, #3
        JR      NZ, M_8D57
        LD      R3, #%20
M_8D57: LDE     R8, @RR4
        LDE     @RR6, R8
        DEC     R5
        DEC     R7
        DJNZ    R3, M_8D57
        RET
        LD      @%3A, #%F6
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
