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

        JP      M_89C9

        .repeat %0d
          nop
        .end

M_8010: PUSH    RP
        SRP     #%60
        LD      R0, #%FC
        LD      R1, %5C
        RL      R1
        RL      R1
        ADD     R1, %5C
        RLC     R1
        RLC     R1
        ADD     R1, %5B
        LD      R2, %5A
        LDE     @RR0, R2
        LD      R0, #%60
        LD      R1, #0
        LDE     @RR0, R1
        LD      R1, %5C
        LD      R2, %5C
        ADD     R2, R2
        ADD     R1, R2
        ADD     R1, #%94
        LD      R0, #%80        ; 8094
        LD      R14, #%62
        LDEI    @R14, @RR0
        LDEI    @R14, @RR0
        LDEI    @R14, @RR0
        ADD     R3, %5B
        ADC     R2, #0
        ADD     R3, %5B
        ADC     R2, #0
        LD      R1, %5A
        SWAP    R1
        LD      R0, R1
        AND     R0, #%0F
        AND     R1, #%F0
        RCF
        RLC     R1
        RLC     R0
        ADD     R1, #%98
        ADC     R0, #%80
        LD      R14, #%10
.1:     TM      %3, #4
        JR      NZ, .1
        LDE     R5, @RR0
        LDE     @RR2, R5
        INCW    R0
        INCW    R2
        LDE     R5, @RR0
        LDE     @RR2, R5
        INCW    R0
        ADD     R3, #%27
        ADC     R2, #0
        DJNZ    R4, .2
        LD      R4, #3
        ADD     R3, #8
        ADC     R2, #0
.2:     DJNZ    R14, .1
        POP     RP
        RET

        nop
        nop

M_8094:
        .data                 %40 %00 %03 %42  %a8 %02 %45 %50 %01 %48 %00 %03
        .data %4a %a8 %02 %4d %50 %01 %50 %00  %03 %52 %a8 %02 %55 %50 %01 %58
        .data %00 %03 %5a %a8 %02 %5d %50 %01  %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00  %ff %ff %ff %ff %ff %ff %ff %ff
        .data %ff %ff %ff %ff %ff %ff %ff %ff  %ff %ff %ff %ff %ff %ff %ff %ff
        .data %ff %ff %ff %ff %ff %ff %ff %ff  %00 %00 %03 %c0 %0e %70 %18 %18
        .data %30 %0c %30 %0c %60 %06 %60 %06  %60 %06 %60 %06 %30 %0c %30 %0c
        .data %18 %18 %0e %70 %03 %c0 %00 %00  %ff %ff %fc %3f %f0 %0f %e0 %07
        .data %c0 %03 %c0 %03 %80 %01 %80 %01  %80 %01 %80 %01 %c0 %03 %c0 %03
        .data %e0 %07 %f0 %0f %fc %3f %ff %ff  %00 %00 %03 %c0 %0f %f0 %1f %f8
        .data %3f %fc %3f %fc %7f %fe %7f %fe  %7f %fe %7f %fe %3f %fc %3f %fc
        .data %1f %f8 %0f %f0 %03 %c0 %00 %00  %ff %ff %fc %3f %f1 %8f %e7 %e7
        .data %cf %f3 %cf %f3 %9f %f9 %9f %f9  %9f %f9 %9f %f9 %cf %f3 %cf %f3
        .data %e7 %e7 %f1 %8f %fc %3f %ff %ff  %00 %00 %00 %00 %00 %00 %01 %80
        .data %03 %c0 %03 %c0 %01 %80 %00 %00  %00 %00 %01 %80 %03 %c0 %03 %c0
        .data %01 %80 %00 %00 %00 %00 %00 %00  %00 %00 %03 %c0 %0e %70 %18 %78
        .data %18 %78 %30 %cc %30 %cc %31 %8c  %31 %8c %33 %0c %33 %0c %1e %18
        .data %1e %18 %0e %70 %03 %c0 %00 %00  %00 %00 %01 %80 %03 %80 %07 %80
        .data %0d %80 %19 %80 %01 %80 %01 %80  %01 %80 %01 %80 %01 %80 %01 %80
        .data %01 %80 %01 %80 %1f %f8 %00 %00  %00 %00 %03 %c0 %0e %70 %18 %18
        .data %30 %0c %20 %0c %00 %0c %00 %18  %00 %f0 %03 %80 %0e %00 %18 %00
        .data %30 %04 %30 %0c %3f %fc %00 %00  %00 %00 %07 %c0 %1c %70 %30 %18
        .data %00 %0c %00 %0c %00 %18 %03 %f0  %00 %38 %00 %0c %00 %0c %20 %0c
        .data %30 %18 %1c %70 %07 %c0 %00 %00  %00 %00 %00 %c0 %01 %80 %03 %00
        .data %06 %00 %0c %00 %18 %c0 %18 %c0  %18 %c0 %1f %f8 %00 %c0 %00 %c0
        .data %00 %c0 %00 %c0 %01 %e0 %00 %00  %00 %00 %3f %f8 %30 %00 %30 %00
        .data %30 %00 %30 %00 %3f %e0 %00 %70  %00 %18 %00 %0c %00 %0c %00 %0c
        .data %30 %18 %1c %70 %07 %c0 %00 %00  %00 %00 %00 %f8 %03 %80 %0e %00
        .data %18 %00 %18 %00 %33 %e0 %3e %70  %30 %18 %30 %0c %30 %0c %30 %0c
        .data %18 %18 %0e %70 %03 %c0 %00 %00  %00 %00 %1f %f8 %1c %18 %18 %18
        .data %10 %18 %00 %30 %00 %60 %00 %c0  %00 %c0 %01 %80 %01 %80 %01 %80
        .data %01 %80 %01 %80 %03 %c0 %00 %00  %00 %00 %03 %c0 %0e %70 %18 %18
        .data %18 %18 %18 %18 %0c %30 %07 %e0  %0c %30 %18 %18 %18 %18 %18 %18
        .data %18 %18 %0e %70 %03 %c0 %00 %00  %00 %00 %03 %c0 %0e %70 %18 %18
        .data %18 %18 %18 %18 %0c %18 %07 %f8  %00 %18 %00 %30 %00 %30 %00 %60
        .data %00 %c0 %03 %80 %07 %00 %00 %00  %00 %00 %01 %80 %03 %c0 %06 %60
        .data %0c %30 %0c %30 %18 %18 %18 %18  %18 %18 %1f %f8 %18 %18 %18 %18
        .data %18 %18 %18 %18 %3c %3c %00 %00  %00 %00 %3f %c0 %18 %70 %18 %18
        .data %18 %0c %18 %0c %18 %18 %1f %f0  %18 %18 %18 %0c %18 %0c %18 %0c
        .data %18 %18 %18 %70 %3f %c0 %00 %00  %00 %00 %03 %c0 %0e %70 %18 %18
        .data %18 %0c %30 %00 %30 %00 %30 %00  %30 %00 %30 %00 %30 %00 %18 %0c
        .data %18 %18 %0e %70 %03 %c0 %00 %00  %00 %00 %3f %80 %18 %e0 %18 %30
        .data %18 %18 %18 %0c %18 %0c %18 %0c  %18 %0c %18 %0c %18 %0c %18 %18
        .data %18 %30 %18 %e0 %3f %80 %00 %00  %00 %00 %3f %fc %18 %0c %18 %04
        .data %18 %00 %18 %00 %18 %40 %1f %c0  %18 %40 %18 %00 %18 %00 %18 %00
        .data %18 %04 %18 %0c %3f %fc %00 %00  %00 %00 %3f %fc %18 %0c %18 %04
        .data %18 %00 %18 %00 %18 %40 %1f %c0  %18 %40 %18 %00 %18 %00 %18 %00
        .data %18 %00 %18 %00 %3c %00 %00 %00  %00 %00 %03 %e0 %0e %38 %18 %0c
        .data %18 %04 %30 %00 %30 %00 %30 %fc  %30 %0c %30 %0c %30 %0c %18 %0c
        .data %18 %1c %0e %6c %03 %cc %00 %00  %00 %00 %3c %3c %18 %18 %18 %18
        .data %18 %18 %18 %18 %18 %18 %1f %f8  %18 %18 %18 %18 %18 %18 %18 %18
        .data %18 %18 %18 %18 %3c %3c %00 %00  %00 %00 %07 %e0 %01 %80 %01 %80
        .data %01 %80 %01 %80 %01 %80 %01 %80  %01 %80 %01 %80 %01 %80 %01 %80
        .data %01 %80 %01 %80 %07 %e0 %00 %00  %00 %00 %3c %00 %18 %00 %18 %00
        .data %18 %00 %18 %00 %18 %00 %18 %00  %18 %00 %18 %00 %18 %00 %18 %00
        .data %18 %04 %18 %0c %3f %fc %00 %00  %00 %00 %03 %c0 %0e %70 %18 %18
        .data %18 %18 %30 %0c %30 %0c %30 %0c  %30 %0c %30 %0c %30 %0c %18 %18
        .data %18 %18 %0e %70 %03 %c0 %00 %00  %00 %00 %3f %c0 %18 %70 %18 %18
        .data %18 %0c %18 %0c %18 %18 %18 %70  %1f %c0 %18 %00 %18 %00 %18 %00
        .data %18 %00 %18 %00 %3c %00 %00 %00  %00 %00 %07 %e0 %1c %38 %30 %0c
        .data %30 %04 %30 %00 %18 %00 %0f %80  %01 %f0 %00 %18 %00 %0c %20 %0c
        .data %30 %0c %1c %38 %07 %e0 %00 %00  %00 %00 %3f %fc %31 %8c %21 %84
        .data %01 %80 %01 %80 %01 %80 %01 %80  %01 %80 %01 %80 %01 %80 %01 %80
        .data %01 %80 %01 %80 %03 %c0 %00 %00  %00 %00 %3f %fc %30 %0c %20 %18
        .data %00 %30 %00 %60 %00 %c0 %01 %80  %03 %00 %06 %00 %0c %00 %18 %00
        .data %30 %04 %30 %0c %3f %fc %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %03 %fc %00 %03 %f8 %00
        .data %03 %f0 %00 %03 %f8 %00 %03 %fc  %00 %03 %fe %00 %03 %7f %00 %02
        .data %3e %00 %00 %1c %00 %00 %08 %00  %ff %ff %00 %fc %3f %00 %f0 %0f
        .data %00 %e0 %07 %00 %c0 %03 %00 %c0  %03 %00 %80 %01 %00 %80 %01 %00
        .data %80 %01 %00 %80 %01 %00 %c0 %03  %00 %c0 %03 %00 %e0 %07 %00 %f0
        .data %0f %00 %fc %3f %00 %ff %ff %00

M_8538: LD      %4B, #8
        LD      %4C, #%84
        LD      %4D, #%D8
        LD      %52, #%85
        LD      %53, #8
        JP      %0830

;============================
M_854A: SRP     #%20
        CLR     %5C
        LD      R0, #%85
        LD      R1, #%71
M_8552: CLR     %5B
M_8554: LD      R2, #%5A
        LDEI    @R2, @RR0
        OR      %5A, %5A
        JR      Z, M_8570
        CP      %5A, #%8D
        JR      Z, M_856C
        CALL    M_8010
        INC     %5B
        CP      %5B, #%14
        JR      C, M_8554
M_856C: INC     %5C
        JR      M_8552

M_8570: RET

M_8571: .data   %01 %01 %01 %1D %01 %20 %01 %1a %01 %17 %01 %1c %01 %1c %01 %1d

M_8581:
        .data     %8d %8d %01 %13 %14 %15 %16  %17 %18 %19 %1a %8d %0a %03 %02
        .data %03 %02 %03 %02 %03 %02 %0a %01  %06 %08 %8d %0b %02 %03 %02 %03
        .data %02 %03 %02 %03 %0b %01 %04 %08  %8d %0c %03 %02 %03 %02 %03 %02
        .data %03 %02 %0c %8d %0d %02 %03 %02  %05 %06 %03 %02 %03 %0d %01 %21
        .data %17 %1b %1c %17 %01 %08 %8d %0e  %03 %02 %03 %06 %05 %02 %03 %02
        .data %0e %01 %1f %1e %13 %1c %20 %17  %08 %8d %0f %02 %03 %02 %03 %02
        .data %03 %02 %03 %0f %8d %10 %03 %02  %03 %02 %03 %02 %03 %02 %10 %8d
        .data %11 %02 %03 %02 %03 %02 %03 %02  %03 %11 %8d %01 %13 %14 %15 %16
        .data %17 %18 %19 %1a %00

;============================
M_8605: SRP     #%50
        LD      R10, #%0C
        CALL    %1905
        LD      R12, #7
        LD      R11, #%21
        CALL    %082D
        .data   "(Sie)\r\r" %00

        LD      R11, #%21
        CALL    %082D
        .data   "(Ich)" %00

        LD      R12, #%13
        LD      R11, #%16
        CALL    %082D
        .data   "Copyright by\r" %00

        LD      R11, #%16
        CALL    %082D
        .data   "SINGER-SOFT:\r" %00

        LD      R11, #%16
        CALL    %082D
        .data   " Thomas Singer\r" %00

        LD      R11, #%16
        CALL    %082D
        .data   "              \r" %00

        LD      R11, #%16
        CALL    %082D
        .data   "       Chemnitz" %00

        JP      M_854A

M_8690: SRP     #%10
        LD      R2, %34
        LD      R3, %35
        CALL    %0182
        LD      %5B, #%0D
        LD      R11, #%17
M_869E: LD      R4, @R11
        CP      R4, #%30
        JR      NZ, M_86B6
        INC     R11
        CP      R11, #%1A
        JR      C, M_869E
M_86AB: LD      %5A, R4
        SUB     %5A, #%27
M_86B0: CALL    M_8010
        INC     %5B
        RET

M_86B6: CALL    M_86AB
        INC     R11
        LD      R4, @R11
        CP      R11, #%1A
        JR      C, M_86B6
        RET

M_86C2: LD      %5C, #3
        LD      %34, %30
        LD      %35, %31
        CALL    M_8690
        INC     %5C
        LD      %34, %32
        LD      %35, %33
        JP      M_8690

M_86D9: PUSH    RP
        SRP     #%60
        JR      M_86E2

M_86DF: AND     %6C, #%7F
M_86E2: LD      %5F, R13
        CALL    %081B
        LD      %13, R13
        OR      R13, R13
        JR      Z, M_86DF
        LD      R0, #%1F
        AND     R0, R12
        LD      R15, %60
M_86F3: OR      R15, R15
        JR      NZ, M_86F3
        CP      %5F, %6D
        JR      Z, M_8701
        AND     %6C, #%7F
        JR      M_871A
M_8701: OR      R12, R12
        JR      MI, M_871A
        LD      R15, #%80
M_8707: CALL    %081B
        OR      R13, R13
        JR      Z, M_86DF
        CP      %5F, %6D
        JR      NZ, M_86DF
        OR      R15, R15
        JR      NZ, M_8707
        OR      %6C, #%80
M_871A: JR      Z, M_871A               ; M_8722
        POP     RP
        RET

M_871F: CLR     %4E
        LD      %4F, %38
        SWAP    %4F
        AND     %4F, #%70
        ADD     %4F, #%10
        CLR     %50
        LD      %51, %39
        SWAP    %51
        AND     %51, #%70
        ADD     %51, #%30
        LD      %5B, #%12
        LD      %5C, #6
        LD      %5A, %39
        ADD     %5A, #%0A
        CALL    M_8010
        INC     %5C
        LD      %5A, %38
        ADD     %5A, #%13
        CALL    M_8010
        CALL    M_8538
        CALL    M_86C2
        CALL    M_86D9
        CALL    %0833
        CP      %13, #%0E
        JR      NZ, M_8766
        SCF
        RET

M_8766: CP      %13, #%0D
        JR      NZ, M_876C
        RET

M_876C: CP      %13, #%31
        JR      C, M_8793
        CP      %13, #%39
        JR      NC, M_877E
        SUB     %13, #%31
        LD      %39, %13
M_877C: JR      M_871F

M_877E: AND     %13, #%DF
        CP      %13, #%41
        JR      C, M_877C
        CP      %13, #%49
        JR      NC, M_877C
        SUB     %13, #%41
        LD      %38, %13
        JR      M_877C

M_8793: CP      %13, #1
        JR      NZ, M_87AA
        OR      %38, %38
        JR      Z, M_877C
        DEC     %38
        LD      %3A, #%FE
M_87A2: LD      %3B, #0
M_87A5: CALL    M_87E0
        JR      M_877C

M_87AA: CP      %13, #2
        JR      NZ, M_87BB
        CP      %38, #7
        JR      NC, M_877C
        INC     %38
        LD      %3A, #2
        JR      M_87A2

M_87BB: CP      %13, #3
        JR      NZ, M_87CF
        OR      %39, %39
        JR      Z, M_877C
        DEC     %39
        LD      %3B, #%FE
M_87CA: LD      %3A, #0
        JR      M_87A5

M_87CF: CP      %13, #4
        JR      NZ, M_877C
        CP      %39, #7
        JR      NC, M_877C
        INC     %39
        LD      %3B, #2
        JR      M_87CA

M_87E0: ADD     %4F, %3A
        ADD     %51, %3B
        CALL    M_8538
M_87E9: TM      %3, #4
        JR      Z, M_87E9
        CALL    %0833
        TM      %4F, #%0F
        JR      NZ, M_87E0
        TM      %51, #%0F
        JR      NZ, M_87E0
        RET

M_87FC: SRP     #%20
        LD      R0, #%FC
        LD      R1, #%3D
        LD      %5C, #3
        LD      R2, #8
M_8807: LD      R3, #8
        LD      %5B, #1
M_880C: LD      R4, #%5A
        LDEI    @R4, @RR0
        CALL    M_86B0
        DJNZ    R3, M_880C
        ADD     R1, #%0C
        INC     %5C
        DJNZ    R2, M_8807
        RET

M_881D: LD      %3B, %39
        RL      %3B
        RL      %3B
        ADD     %3B, %39
        RL      %3B
        RL      %3B
        ADD     %3B, %38
        LD      %3A, #%FC
        ADD     %3B, #%3D
        RET

M_8835: SRP     #%20
        LDE     R3, @RR0
        AND     R3, #%3E
        CP      R3, R2
        RET

M_883F: LD      %3C, #%FF
        SRP     #%20
        LD      R0, %3A
        LD      R1, %3B
M_8848: INC     %3C
        ADD     R1, %3D
        CALL    M_8835
        JR      Z, M_8848
        LD      R4, #%0A
        SUB     R4, R2
        CP      R4, R3
        JR      Z, M_885C
        CLR     %3C
M_885C: RET

M_885D: CALL    M_883F
        OR      %3C, %3C
        JR      Z, M_885C
        LD      R0, %3A
        LD      R1, %3B
M_8869: ADD     R1, %3D
        CALL    M_8835
        JR      NZ, M_885C
        LDE     R5, @RR0
        AND     R5, #1
        OR      R5, R4
        LDE     @RR0, R5
        JR      M_8869

M_887C: .data   %EB %EC %ED %FF %01 %13 %14 %15

M_8884: SRP     #%20
        LD      R12, #%88
        LD      R13, #%7C
        LD      R14, #8
        LD      R15, #0
M_888E: LD      R11, #%3D
        LDEI    @R11, @RR12
        CALL    M_883F
        ADD     %2F, %3C
        DJNZ    R14, M_888E
        LD      %3C, R15
        RET
M_889D: SRP     #%20
        LD      R12, #%88
        LD      R13, #%7C
        LD      R14, #8
M_88A5: LD      R11, #%3D
        LDEI    @R11, @RR12
        CALL    M_885D
        DJNZ    R14, M_88A5
        RET

M_88AF: SRP     #%20
        LD      R2, #2
        CALL    M_881D
        LD      R0, %3A
        LD      R1, %3B
        CALL    M_8835
        JR      Z, M_88D5
M_88BF: LD      PRE1, #%23
        LD      T1, #%70
        LD      TMR, #%8A
        LD      %6F, #%30
M_88CB: OR      %6F, %6F
        JR      NZ, M_88CB
        LD      TMR, #2
        SCF
        RET
M_88D5: LD      R2, #4
        CALL    %8884           ;!!!
        OR      R15, R15
        JR      Z, M_88BF
        ADD     %31, %3C
        ADC     %30, #0
        LD      R0, %3A
        LD      R1, %3B
        LDE     R5, @RR0
        AND     R5, #1
        OR      R5, R4
        LDE     @RR0, R5
        CALL    M_889D
        CALL    M_87FC
        RCF
        RET

M_88F9: SRP     #%20
        LD      %36, #%3D
        CLR     %37
        LD      %3A, #%FC
        LD      %3B, #%3C
        LD      %3E, #8
M_8909: LD      %3F, #8
M_890C: INC     %3B
        LD      R0, %3A
        LD      R1, %3B
        LD      R2, #2
        CALL    M_8835
        JR      Z, M_892B
M_8919: DEC     %3F
        JR      NZ, M_890C
        ADD     %3B, #%0C
        DEC     %3E
        JR      NZ, M_8909
        OR      %37, %37
        JR      NZ, M_8947
        SCF
        RET
M_892B: LD      R2, #6
        CALL    %8884           ;!!!
        CP      %3C, %37
        JR      C, M_8919
        JR      NZ, M_893F
        CALL    %0836
        TM      %74, #1
        JR      Z, M_8919
M_893F: LD      %36, %3B
        LD      %37, %3C
        JR      M_8919
M_8947: ADD     %33, %37
        ADC     %32, #0
        LD      %3B, %36
        LD      %22, #6
        CALL    M_889D
        LD      R0, #%FC
        LD      R1, %36
        LDE     R5, @RR0
        AND     R5, #1
        OR      R5, R4
        LDE     @RR0, R5
        CALL    M_87FC
        RCF
        RET

M_8968: SRP     #%20
        LD      %3A, #%FC
        LD      %3B, #%3C
        LD      %3E, #8
M_8973: LD      %3F, #8
M_8976: INC     %3B
        LD      R0, %3A
        LD      R1, %3B
        LD      R2, #2
        CALL    M_8835
        JR      Z, M_8990
M_8983: DEC     %3F
        JR      NZ, M_8976
        ADD     %3B, #%0C
        DEC     %3E
        JR      NZ, M_8973
        SCF
        RET
M_8990: LD      R2, #4
        CALL    M_8884
        OR      %3C, %3C
        JR      Z, M_8983
        RCF
        RET

;============================
M_899C: CLR     %30
        CLR     %31
        CLR     %32
        CLR     %33
        CLR     %4A
        CALL    M_8605
        LD      %38, #4
        LD      %39, #4
M_89AF: CALL    M_871F
        JR      NC, M_89B5
        RET
M_89B5: CALL    M_88AF
        JR      C, M_89AF
        CALL    M_88F9
        JR      C, M_89C8
        CALL    M_86C2
        CALL    M_8968
        JR      NC, M_89AF
        RCF
M_89C8: RET

;============================
M_89C9: CALL    M_899C
        JR      C, M_8A06
        CALL    %86D9
        CALL    %082D
        .data   %0c %00

        LD      %5B, #5
        LD      %5C, #%0C
        CALL    %082D
        .data   "Noch ein Spiel (J/N) ?" %00

M_89F6: CALL    %081E
        AND     %13, #%5F
        CP      %13, #%4A
        JR      Z, M_89C9
        CP      %13, #%4E
        JR      NZ, M_89F6
M_8A06: CALL    %082D
        .data   %0c "         Auf Wiedersehen!\r" %00
        ret

M_8A26: .data                         %46 %00  %07 %07 %77 %77 %07 %07 %60 %60
        .data %67 %77 %07 %77 %77 %07 %07 %06  %06 %00 %75 %55 %77 %77 %07 %00
        .data %60 %60 %60 %75 %55 %77 %77 %07  %77 %07 %07 %00 %75 %55 %77 %77
        .data %00 %00 %07 %07 %60 %75 %55 %77  %77 %77 %77 %00 %07 %00 %75 %55
        .data %77 %77 %77 %77 %77 %77 %07 %70  %77 %77 %77 %77 %77 %77 %70 %00
        .data %00 %77 %77 %77 %77 %77 %77 %70  %00 %00 %77 %77 %77 %77 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00  %00 %00 %00 %00 %00 %00 %00 %00
