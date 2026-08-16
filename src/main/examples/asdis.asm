        .ORG %C100

        .data "D" %95 %95 %95 %04 %F8 %38 %11
        CALL    %0CA9   ; getHexWordFromRR14_forgetCallerIfError
        JR      M_C352
        .align 8, %ff

        .data "." %95 %95 %95 %08 %e8 %07 %51
        CALL    %0CA9   ; getHexWordFromRR14_forgetCallerIfError
        JR      M_C9B4
        .align %100, %ff

M_C200: .data     %F7 %F6 %F4 %F2 %E2 %D5 %C6 %C5 %C4 %97 %96 %95 %94 %87 %86 %85 %84 %7F %6F %5F %4F %3F %2F %1F %0F
        NOP

M_C21A: .data "LD"   %A0 ; ' ' 0
        .data "DJNZ" %A0 ; ' ' 1
        .data "JR"   %A0 ; ' ' 2
        .data "JP"   %A0 ; ' ' 3
        .data "BYTE" %A0 ; ' ' 4
        .data "D"    %C9 ; 'I' 5
        .data "E"    %C9 ; 'I' 6
        .data "RE"   %D4 ; 'T' 7
        .data "IRE"  %D4 ; 'T' 8
        .data "RC"   %C6 ; 'F' 9
        .data "SC"   %C6 ; 'F' %a
        .data "CC"   %C6 ; 'F' %b
        .data "NO"   %D0 ; 'P' %c
        .data "ADD"  %A0 ; ' ' %d
        .data "ADC"  %A0 ; ' ' %e
        .data "SUB"  %A0 ; ' ' %f
        .data "SBC"  %A0 ; ' ' %10
        .data "OR"   %A0 ; ' ' %11
        .data "AND"  %A0 ; ' ' %12
        .data "TCM"  %A0 ; ' ' %13
        .data "TM"   %A0 ; ' ' %14
        .data %A0        ; ' ' %15
        .data %A0        ; ' ' %16
        .data "CP"   %A0 ; ' ' %17
        .data "XOR"  %A0 ; ' ' %18
        .data "LDE"  %A0 ; ' ' %19
        .data "LDEI" %A0 ; ' ' %1a
        .data "LDC"  %A0 ; ' ' %1b
        .data "LDCI" %A0 ; ' ' %1c
        .data "CALL" %A0 ; ' ' %1d
        .data "SRP"  %A0 ; ' ' %1e
        .data "DEC"  %A0 ; ' ' %1f
        .data "RLC"  %A0 ; ' ' %20
        .data "INC"  %A0 ; ' ' %21
        .data %A0        ; ' ' %22
        .data "DA"   %A0 ; ' ' %23
        .data "POP"  %A0 ; ' ' %24
        .data "COM"  %A0 ; ' ' %25
        .data "PUSH" %A0 ; ' ' %26
        .data "DECW" %A0 ; ' ' %27
        .data "RL"   %A0 ; ' ' %28
        .data "INCW" %A0 ; ' ' %29
        .data "CLR"  %A0 ; ' ' %2a
        .data "RRC"  %A0 ; ' ' %2b
        .data "SRA"  %A0 ; ' ' %2c
        .data "RR"   %A0 ; ' ' %2d
        .data "SWAP" %A0 ; ' ' %2e
        .data %C6        ; 'F' %2f
        .data "L"    %D4 ; 'T' %30
        .data "L"    %C5 ; 'E' %31
        .data "UL"   %C5 ; 'E' %32
        .data "O"    %D6 ; 'V' %33
        .data "M"    %C9 ; 'I' %34
        .data %DA        ; 'Z' %35
        .data %C3        ; 'C' %36
        .data %A0        ; ' ' %37
        .data "G"    %C5 ; 'E' %38
        .data "G"    %D4 ; 'T' %39
        .data "UG"   %D4 ; 'T' %3a
        .data "NO"   %D6 ; 'V' %3b
        .data "P"    %CC ; 'L' %3c
        .data "N"    %DA ; 'Z' %3d
        .data "N"    %C3 ; 'C' %3E
        .align %10, %ff

printHashPercentHex8:
        LD      R5, #'#'
printCharPercentHex8:
        CALL    %0818              ;CHAROUT
printPercentHex8:
        LD      R5, #'%'
        CALL    %0818              ;CHAROUT
        JR       %0C72

printAtReg:
        LD      R5, #'@'
        JR      printCharPercentHex8

printAtRHex4:
        LD      R5, #'@'
        CALL    %0818              ;CHAROUT
printRHex4HiNibble:
        SWAP    %19
printRHex4:
        LD      R5, #'R'
        CALL    %0818              ;CHAROUT
        JR       %0C7B ; printHex4LoNibble

printAtRRHex4:
        LD      R5, #'@'
        CALL    %0818              ;CHAROUT
        LD      R5, #'R'
        CALL    %0818              ;CHAROUT
        JR      printRHex4

printComma:
        LD      R5, #','
        JR       %0818

        NOP

        ; print n-th string from C21A
printNthString:
        CP      %10, #%3F
        JR      UGE, M_C348
        LD      R14, #hi(M_C21A)
        LD      R15, #lo(M_C21A)
        OR      R0, R0
        JR      Z, M_C339
M_C32F: LDE     R1, @RR14
        INCW    %1E
        OR      R1, R1
        JR      PL, M_C32F
        DJNZ    R0, M_C32F
M_C339: LDE     R1, @RR14
        LD      R5, #%7F
        AND     R5, R1
        CALL    %0818              ;CHAROUT
        INCW    %1E
        OR      R1, R1
        JR      PL, M_C339
M_C348: RET

        NOP

M_C34A: INCW    %1A
        CALL    %0C91 ; PrintlnWaitForSpaceToContinue
        JR      Z, M_C355
        RET

M_C352: NOP
        NOP
        NOP
        ; disassembler
M_C355: LD      R5, #'.'
        CALL    %0C9B   ; printCharWordSpace
        ; check for an illegal op-code
        LDE     R9, @RR10
        LD      R12, #hi(M_C200)
        LD      R13, #lo(M_C200)
        LD      R1, #%19
M_C362: LDE     R0, @RR12
        CP      R0, R9
        JR      Z, M_C36E
        INCW    %1C
        DJNZ    R1, M_C362
        JR      M_C378

        ; print BYTE
M_C36E: LD      R0, #4
        CALL    printNthString
        CALL    printPercentHex8
M_C376: JR      M_C34A

        ; valid op-code
M_C378: NOP
        NOP

        LD      R0, #%0F
        AND     R0, R9
        CP      %10, #%0F
        JR      NZ, M_C391
        ; xF
        SWAP    %19
        LD      R0, #%0F
        AND     R0, R9
        ADD     %10, #%FD  ; -3
        CALL    printNthString
M_C38F: JR      M_C376

M_C391: CP      %10, #%0E
        JR      NZ, M_C3A0
        ; xE -> INC Ra
        LD      R0, #%21
        CALL    printNthString
        CALL    printRHex4HiNibble
M_C39E: JR      M_C38F

M_C3A0: CP      %10, #8
        JR      NZ, M_C3B9
        ; x8 -> LD Ra,aa
        LD      R0, #0
        CALL    printNthString
        CALL    printRHex4HiNibble
        INCW    %1A
        CALL    printComma
        LDE     R9, @RR10
        CALL    printPercentHex8
M_C3B7: JR      M_C39E

M_C3B9: NOP
        NOP
        CP      %10, #9
        JR      NZ, M_C3D8
        ; x9 -> LD aa,Ra
        LD      R0, #0
        CALL    printNthString
        INCW    %1A
        LD      R0, %19
        LDE     R9, @RR10
        CALL    printPercentHex8
        CALL    printComma
        LD      R9, %10
        CALL    printRHex4HiNibble
M_C3D6: JR      M_C3B7

M_C3D8: CP      %10, #%0C
        JR      NZ, M_C3F1
        ; xC -> LD Ra,#%aa
        LD      R0, #0
        CALL    printNthString
        CALL    printRHex4HiNibble
M_C3E5: CALL    printComma
M_C3E8: INCW    %1A
        LDE     R9, @RR10
        CALL    printHashPercentHex8
M_C3EF: JR      M_C3D6

M_C3F1: CP      %10, #%0A
        JR      NZ, M_C425
        ; xA -> DJNZ Ra,%aaaa
        LD      R0, #1
        CALL    printNthString
        CALL    printRHex4HiNibble
M_C3FE: CALL    printComma
M_C401: INCW    %1A
        LD      R5, #'%'
        CALL    %0818              ;CHAROUT
        LDE     R9, @RR10
        OR      R9, R9
        JR      MI, M_C41A
        LD      R8, #0
        SCF
        ADC     R9, R11
        ADC     R8, R10
M_C415: CALL    %0C69   ; printHexWord
M_C418: JR      M_C3EF

M_C41A: SCF
        LD      R8, %1A
        ADC     R9, R11
        JR      C, M_C415
        DEC     %18
        JR      M_C415

M_C425: CP      %10, #%0B
        JR      NZ, M_C442
        ; xB -> JR [jc,]%aaaa
        LD      R0, #2
        CALL    printNthString
        SWAP    %19
        AND     %19, #%0F
        CP      %19, #8
        JR      Z, M_C401
        ; jump condition
        LD      R0, #%2F
        ADD     R0, R9
        CALL    printNthString
        JR      M_C3FE

M_C442: CP      %10, #%0D
        JR      NZ, M_C474
        ; xD -> JP [jc,]%aaaa
        LD      R0, #3
        CALL    printNthString
        LD      R0, #%F0
        AND     R0, R9
        SWAP    %10
        NOP
        NOP
        CP      %10, #8
        JR      Z, M_C462
        ADD     %10, #%2F
        CALL    printNthString
        CALL    printComma
M_C462: INCW    %1A
        LD      R5, #'%'
        CALL    %0818              ;CHAROUT
        LDE     R8, @RR10
        INCW    %1A
        LDE     R9, @RR10
        CALL    %0C69   ; printHexWord
M_C472: JR      M_C418

M_C474: CP      %10, #2
        JR      NC, M_C4BB
        ; x0 or x1
        LD      R8, %10
        SWAP    %19
        AND     %19, #%0F
        CP      %19, #3
        JR      NZ, cmd_x0_x1
        ; 3x
        OR      R8, R8
        JR      NZ, cmd_31
        ; JP @%aa
        LD      R0, #3
M_C48B: CALL    printNthString
        INCW    %1A
        LDE     R9, @RR10
        CALL    printAtReg
M_C495: JR      M_C472

        ; SRP #%aa
cmd_31: LD      R0, #%1E
        CALL    printNthString
        JR      M_C3E8

        ; normal x0/x1 commands
cmd_x0_x1:
        LD      R0, #%1F
        ADD     R0, R9
        NOP
        NOP
        NOP
        CALL    printNthString
        INCW    %1A
        LDE     R9, @RR10
        OR      R8, R8
        JR      Z, cmd_x1
        ; x1
        CALL    printAtReg
M_C4B4: JR      M_C495

cmd_x1: CALL    printPercentHex8
M_C4B9: JR      M_C4B4

M_C4BB: CP      %19, #%D6
        JR      NZ, M_C4C7
        ; D6 -> CALL %aaaa
        LD      R0, #%1D
        CALL    printNthString
        JR      M_C462

M_C4C7: NOP
        NOP
        CP      %19, #%D4
        JR      NZ, M_C4D2
        ; D4 -> CALL @%aa
        LD      R0, #%1D
        JR      M_C48B

M_C4D2: CP      %19, #%F3
        JR      NZ, M_C4ED
        ; F3 -> LD @Ra,Ra
        LD      R0, #0
        CALL    printNthString
        INCW    %1A
        LDE     R9, @RR10
        CALL    printAtRHex4
        CALL    printComma
        LDE     R9, @RR10
        CALL    printRHex4
M_C4EB: JR      M_C4B9

M_C4ED: CP      %19, #%F5
        JR      NZ, M_C50C
        ; F5 -> LD @%aa,%aa
        LD      R0, #0
        CALL    printNthString
        INCW    %1A
        LDE     R8, @RR10
        INCW    %1A
        LDE     R9, @RR10
        CALL    printAtReg
        CALL    printComma
        LD      R9, %18
        CALL    printPercentHex8
M_C50A: JR      M_C4EB

M_C50C: CP      %19, #%C7
        JR      NZ, M_C53A
        ; C7 -> LD Ra,%aa(Ra)
        INCW    %1A
        LD      R0, #0
        CALL    printNthString
        LDE     R9, @RR10
        LD      R8, %19
        CALL    printRHex4HiNibble
        CALL    printComma
        INCW    %1A
        LDE     R9, @RR10
        CALL    printPercentHex8
        LD      R5, #'('
        CALL    %0818              ;CHAROUT
        LD      R9, %18
        CALL    printRHex4
        LD      R5, #')'
        CALL    %0818              ;CHAROUT
M_C538: JR      M_C50A

M_C53A: NOP
        NOP
        CP      %19, #%D7
        JR      NZ, M_C56A
        ; D7 -> LD %aa(%Ra),Ra
        LD      R0, #0
        CALL    printNthString
        INCW    %1A
        LDE     R8, @RR10
        INCW    %1A
        LDE     R9, @RR10
        CALL    printPercentHex8
        LD      R5, #'('
        CALL    %0818              ;CHAROUT
        LD      R9, %18
        CALL    printRHex4
        LD      R5, #')'
        CALL    %0818              ;CHAROUT
        CALL    printComma
        LD      R9, %18
        CALL    printRHex4HiNibble
M_C568: JR      M_C538

M_C56A: CP      %19, #%82
        JR      NZ, M_C585
        ; 82 -> LDE Ra,@RRa
        LD      R0, #%19
M_C571: CALL    printNthString
        INCW    %1A
        LDE     R9, @RR10
        CALL    printRHex4HiNibble
M_C57B: CALL    printComma
        LDE     R9, @RR10
        CALL    printAtRRHex4
M_C583: JR      M_C568

M_C585: CP      %19, #%C2
        JR      NZ, M_C58E
        ; C2 -> LDC Ra, @RRa
        LD      R0, #%1B
        JR      M_C571

M_C58E: CP      %19, #%83
        JR      NZ, M_C5A1
        ; 83 -> LDEI @Ra,@RRa
        LD      R0, #%1A
M_C595: CALL    printNthString
        INCW    %1A
        LDE     R9, @RR10
        CALL    printAtRHex4
        JR      M_C57B

M_C5A1: CP      %19, #%C3
        JR      NZ, M_C5AA
        ; C3 -> LDCI @Ra,@RRa
        LD      R0, #%1C
        JR      M_C595

M_C5AA: CP      %19, #%92
        JR      NZ, M_C5C5
        ; 92 -> LDE @RRa,Ra
        LD      R0, #%19
M_C5B1: CALL    printNthString
        INCW    %1A
        LDE     R9, @RR10
        CALL    printAtRRHex4
        CALL    printComma
        LDE     R9, @RR10
        CALL    printRHex4HiNibble
M_C5C3: JR      M_C583

M_C5C5: CP      %19, #%D2
        JR      NZ, M_C5CE
        ; D2 -> LDC @RRa,Ra
        LD      R0, #%1B
        JR      M_C5B1

M_C5CE: CP      %19, #%93
        JR      NZ, M_C5E9
        ; 93 -> LDEI @RRa,@Ra
        LD      R0, #%1A
M_C5D5: CALL    printNthString
        INCW    %1A
        LDE     R9, @RR10
        CALL    printAtRRHex4
        CALL    printComma
        LDE     R9, @RR10
        CALL    printAtRHex4
M_C5E7: JR      M_C5C3

M_C5E9: CP      %19, #%D3
        JR      NZ, M_C5F2
        ; D3 -> LDCI @RRa,@Ra
        LD      R0, #%1C
        JR      M_C5D5

M_C5F2: LD      R12, #%0F
        NOP
        AND     R12, R9
        AND     %19, #%F0
        CP      %19, #%E0
        JR      NZ, M_C603
        ; Ex -> LD 
        LD      R0, #0
        JR      M_C609

M_C603: SWAP    %19
        LD      R0, #%0D
        ADD     R0, R9
M_C609: CALL    printNthString
        INCW    %1A
        DEC     %1C
        DJNZ    R12, M_C621
        LDE     R9, @RR10
        CALL    printRHex4HiNibble
        CALL    printComma
        LDE     R9, @RR10
        CALL    printRHex4
M_C61F: JR      M_C5E7

M_C621: DJNZ    R12, M_C637
        LDE     R9, @RR10
        CALL    printRHex4HiNibble
        CALL    printComma
        LD      R5, #'@'
        CALL    %0818              ;CHAROUT
        LDE     R9, @RR10
        CALL    printRHex4
M_C635: JR      M_C61F

M_C637: DJNZ    R12, M_C64C
        LDE     R8, @RR10
        INCW    %1A
        LDE     R9, @RR10
        CALL    printPercentHex8
        CALL    printComma
        LD      R9, %18
        CALL    printPercentHex8
M_C64A: JR      M_C635

M_C64C: DJNZ    R12, M_C661
        LDE     R8, @RR10
        INCW    %1A
        LDE     R9, @RR10
        CALL    printPercentHex8
        CALL    printComma
        LD      R9, %18
        CALL    printAtReg
        JR      M_C64A

M_C661: DJNZ    R12, M_C66B
        LDE     R9, @RR10
        CALL    printPercentHex8
M_C668: JR      M_C3E5

M_C66B: LDE     R9, @RR10
        CALL    printAtReg
        JR      M_C668

        .align %10, %ff

M_C680: LD      R8, #%C2
        LD      R9, #%1A
        LD      R0, #0
M_C686: LD      R3, R15
M_C688: LDE     R5, @RR8
        AND     R5, #%7F
        CP      R5, @R3
        JR      NZ, M_C69F
        INC     R3
        LDE     R5, @RR8
        OR      R5, R5
        JR      PL, M_C69B
        LD      R15, R3
        RET

M_C69B: INCW    R8
        JR      M_C688

M_C69F: INC     R0
        CP      R0, #%3F
        JR      C, M_C6A8
        LD      R0, #%FF
        RET

M_C6A8: LDE     R5, @RR8
        INCW    R8
        OR      R5, R5
        JR      PL, M_C6A8
        JR      M_C686

        NOP
        NOP
getHex4:
        CP      R2, #'0'
        JR      NC, M_C6BA
M_C6B9: RET

M_C6BA: SUB     R2, #'0'
        CP      R2, #%0A
        JR      C, M_C6CD
        CP      R2, #%11
        JR      C, M_C6B9
        SUB     R2, #7
        CP      R2, #%10
M_C6CD: CCF
        RET

        NOP

M_C6D0: CALL    M_C680
        OR      R0, R0
        JR      PL, M_C6DB
        LD      R0, #%80
        RCF
        RET

M_C6DB: INC     R15
        SUB     R0, #%2F
        OR      R0, R0
        JR      MI, M_C6EC
        CP      R0, #%10
        JR      NC, M_C6EC
        SWAP    R0
        RCF
        RET

M_C6EC: SCF
        RET

        .align %10, %ff

M_C6F0: LD      R3, R15
M_C6F2: CP      @%13, #'R'
        JR      Z, M_C6F9
M_C6F7: SCF
M_C6F8: RET

M_C6F9: INC     R3
        LD      R2, @R3
        CALL    getHex4
        JR      C, M_C6F8
        LD      R13, R2
        LD      R15, R3
        INC     R15
        RET

        NOP
M_C708: LD      R3, R15
        CP      @%13, #'@'
M_C70D: JR      NZ, M_C6F7
        INC     R3
        JR      M_C6F2

M_C712: LD      R3, R15
        CP      @%13, #'@'
        JR      NZ, M_C6F7
        INC     R3
        CP      @%13, #%52
        JR      M_C70D

        NOP
getPercentHex8:
        LD      R3, R15
_getPercentHex8:
        CP      @%13, #'%'
        JR      NZ, M_C6F7
M_C727: INC     R3
        LD      R2, @R3
        CALL    getHex4
        JR      C, M_C6F7
        LD      R13, R2
        SWAP    R13
        INC     R3
        LD      R2, @R3
        CALL    getHex4
        JR      C, M_C6F7
        OR      R13, R2
        LD      R15, R3
        INC     R15
        RET

        NOP
getAtPercentHex8:
        LD      R3, R15
        CP      @%13, #'@'
M_C747: JR      NZ, M_C6F7
        INC     R3
        JR      _getPercentHex8

getHashPercentHex8:
        LD      R3, R15
        CP      @%13, #'#'
        JR      M_C747

        NOP

getPercentHex8_v2:
        LD      R3, R15
        CP      @%13, #'%'
        JR      NZ, M_C6F7
        INC     R3
        LD      R2, @R3
        CALL    getHex4
        JR      C, M_C6F7
        LD      R12, R2
        SWAP    R12
        INC     R3
        LD      R2, @R3
        CALL    getHex4
        JR      C, M_C6F7
        OR      R12, R2
        JR      M_C727

        NOP
        NOP
        NOP
        NOP
        NOP
assembler:
        LD      R4, #%20
        LD      R15, #%30
        CALL    M_C680
        OR      R0, R0
        JR      PL, M_C784
M_C783: RET

M_C784: CP      R0, #%2F
        JR      NC, M_C783
        CP      R0, #4 ; BYTE
        JR      Z, M_C783
        CP      R0, #%1F
        JR      C, M_C7C2
        ; DEC-SWAP
        LD      R1, R0
        SUB     R0, #%1F
        SWAP    R0
        AND     R0, #%F0
        CALL    getPercentHex8
        JR      NC, M_C7AA
        OR      R0, #1
        CALL    getAtPercentHex8
        JR      C, M_C7B1
M_C7AA: LD      @R4, R0
        INC     R4
M_C7AD: LD      @R4, R13
        INC     R4
M_C7B0: RET

M_C7B1: CP      R1, #%21
        JR      NZ, M_C7B0
        CALL    M_C6F0
        JR      C, M_C7B0
        SWAP    R13
        OR      R13, #%0E
        JR      M_C7AD

M_C7C2: CP      R0, #%1E
        JR      C, M_C7CF
        CALL    getHashPercentHex8
        LD      R0, #%31
        JR      NC, M_C7AA
M_C7CE: RET

M_C7CF: CP      R0, #%1D
        JR      C, M_C7E8
        CALL    getAtPercentHex8
        LD      R0, #%D4
        JR      NC, M_C7AA
        CALL    getPercentHex8_v2
        JR      C, M_C7CE
        LD      @%14, #%D6
        INC     R4
        LD      R0, R12
        JR      M_C7AA

M_C7E8: CP      R0, #%1C
        JR      C, M_C817
        LD      R0, #%C3
M_C7EF: CALL    M_C708
        JR      C, M_C803
        INC     R15
        LD      R1, R13
        CALL    M_C712
        JR      NC, M_C7FD
M_C7FC: RET

M_C7FD: SWAP    R1
M_C7FF: OR      R13, R1
        JR      M_C7AA

M_C803: OR      R0, #%10
        CALL    M_C712
        JR      C, M_C7FC
        LD      R1, R13
        INC     R15
        CALL    M_C708
        JR      C, M_C7FC
M_C813: SWAP    R13
        JR      M_C7FF

M_C817: CP      R0, #%1B
        JR      C, M_C83D
        LD      R0, #%C2
M_C81E: CALL    M_C6F0
        JR      C, M_C82C
        LD      R1, R13
        INC     R15
        CALL    M_C712
        JR      NC, M_C7FD
M_C82B: RET

M_C82C: OR      R0, #%10
        CALL    M_C712
        JR      C, M_C82B
        LD      R1, R13
        INC     R15
        CALL    M_C6F0
        JR      NC, M_C813
        RET

M_C83D: CP      R0, #%1A
        JR      C, M_C846
        LD      R0, #%83
        JR      M_C7EF

M_C846: CP      R0, #%19
        JR      C, M_C84F
        LD      R0, #%82
        JR      M_C81E

M_C84F: CP      R0, #%0D
        JR      C, M_C8BA
        SUB     R0, #%0D
        SWAP    R0
        INC     R0
        INC     R0
        CALL    M_C6F0
        JR      C, M_C876
        LD      R1, R13
        INC     R15
        CALL    M_C6F0
        JR      C, M_C86F
M_C868: SWAP    R1
        OR      R13, R1
        JR      M_C7AA

M_C86F: INC     R0
        CALL    M_C708
        JR      NC, M_C868
        RET

M_C876: INC     R0
        INC     R0
        CALL    getPercentHex8
        LD      R1, R13
        JR      C, M_C8A5
        INC     R15
M_C880: CALL    getPercentHex8
        JR      C, M_C88F
M_C885: LD      @R4, R0
        INC     R4
        LD      @R4, R13
        INC     R4
        LD      @R4, R1
        INC     R4
        RET

M_C88F: INC     R0
        CALL    getAtPercentHex8
        JR      NC, M_C885
        INC     R0
M_C896: CALL    getHashPercentHex8
        JR      C, M_C8A4
M_C89B: LD      @R4, R0
        INC     R4
M_C89E: LD      @R4, R1
        INC     R4
        LD      @R4, R13
        INC     R4
M_C8A4: RET

M_C8A5: INC     R0
        INC     R0
        INC     R0
        CALL    getAtPercentHex8
        JR      C, M_C8A4
        LD      R1, R13
        INC     R15
        CALL    getHashPercentHex8
        JR      NC, M_C89B
        RET
        NOP
        NOP
        NOP
        NOP
M_C8BA: CP      R0, #5
        JR      C, M_C8CB
        SUB     R0, #5
        SWAP    R0
        OR      R0, #%8F
        LD      @R4, R0
        INC     R4
M_C8CA: RET

M_C8CB: CP      R0, #3
        JR      C, M_C8EA
        CALL    getAtPercentHex8
        JR      C, M_C8D9
        LD      R1, #%30
        JR      M_C89E

M_C8D9: CALL    M_C6D0
        JR      C, M_C8CA
        OR      R0, #%0D
        CALL    getPercentHex8_v2
        JR      C, M_C8CA
        LD      R1, R12
        JR      M_C89B

M_C8EA: CP      R0, #2
        JR      C, M_C910
        CALL    M_C6D0
        JR      C, M_C8CA
        OR      R0, #%0B
M_C8F7: CALL    getPercentHex8_v2
        JR      C, M_C8CA
        SUB     R13, R11
        SBC     R12, R10
        DECW    R12
        DECW    R12
        OR      R13, R13
        JR      PL, M_C909
        INC     R12
M_C909: OR      R12, R12
        JR      NZ, M_C8CA
        JR      M_C7AA

M_C910: CP      R0, #1
        JR      C, M_C924
        CALL    M_C6F0
        JR      C, M_C8CA
        LD      R0, R13
        SWAP    R0
        OR      R0, #%0A
        INC     R15
        JR      M_C8F7

M_C924: CALL    M_C6F0
        JR      C, M_C958
        LD      R1, R13
        SWAP    R1
        INC     R15
        CALL    getHashPercentHex8
        JR      C, M_C93A
        LD      R0, #%0C
M_C935: OR      R0, R1
M_C937: JR      M_C7AA

M_C93A: CALL    getPercentHex8
        LD      R12, R13
        JR      C, M_C951
        INC     R15
        CALL    M_C6F0
        LD      R0, #8
        JR      C, M_C935
        LD      R0, #%C7
        OR      R13, R1
        LD      R1, R12
        JR      M_C9B1

M_C951: SWAP    R1
        LD      R0, #%E2
        JR      M_C86F

M_C958: CALL    M_C708
        JR      C, M_C96E
        INC     R15
        LD      R1, R13
        SWAP    R1
        CALL    M_C6F0
        JR      NC, M_C968
        RET

M_C968: OR      R13, R1
        LD      R0, #%F3
        JR      M_C937

M_C96E: CALL    getPercentHex8
        JR      C, M_C99F
        INC     R15
        LD      R1, R13
        CALL    M_C6F0
        JR      NC, M_C980
        LD      R0, #%E4
        JR      M_C880

M_C980: SWAP    R13
        LD      R0, R13
        INC     R15
        INC     R15
        CALL    M_C6F0
        JR      C, M_C998
        OR      R13, R0
        LD      R0, #%D7
        SWAP    R13
        JR      M_C9B1

M_C993: INC     R4
        LD      @R4, R1
        INC     R4
M_C997: RET

M_C998: OR      R0, #9
        LD      @R4, R0
        JR      M_C993

M_C99F: CALL    getAtPercentHex8
        JR      C, M_C997
        LD      R1, R13
        INC     R15
        LD      R0, #%E7
        CALL    getPercentHex8
        JR      C, M_C896
        LD      R0, #%F5
M_C9B1: JR      M_C885

        ; assembler
M_C9B4: LD      R0, #%30
        LD      R1, #%10
M_C9B8: PUSH    @%10
        INC     R0
        DJNZ    R1, M_C9B8
        PUSH    %20
        PUSH    %21
        PUSH    %22
        LD      R0, #%30
        LD      R1, #%10
M_C9C7: LDEI    @R0, @RR14
        DJNZ    R1, M_C9C7
        CALL    assembler
        AND     R4, #%0F
        JR      Z, M_C9DE
        LD      R0, #%20
M_C9D5: LDEI    @RR10, @R0
        DJNZ    R4, M_C9D5
        LD      R5, #%2E
        CALL    %0C9B   ; printCharWordSpace
M_C9DE: POP     %22
        POP     %21
        POP     %20
        LD      R0, #%3F
        LD      R1, #%10
M_C9E8: POP     @%10
        DEC     R0
        DJNZ    R1, M_C9E8
        RET

        NOP
        .data   %0
