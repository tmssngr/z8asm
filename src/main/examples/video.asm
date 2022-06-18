        .ORG    %0800

        ; P35 (20h) = /SYN
        ; P36 (40h) = BUSY
        ; P37 (80h) = if 1 shift from Video-RAM

        .const TMR_P36_NOT_USED = 0b0000_0000
        .const TMR_P36_OUTPUT_T0 = 0b0100_0000
        .const TMR_P36_OUTPUT_T1 = 0b1000_0000
        .const TMR_P36_XTAL = 0b1100_0000
        .const TMR_P31_AS_PRESCALER_SOURCE = 0b0000_0000
        .const TMR_P31_AS_GATE = 0b0001_0000
        .const TMR_P31_START = 0b0010_0000
        .const TMR_P31_RESTART = 0b0011_0000
        .const TMR_T1_DISABLE = 0b0000_0000
        .const TMR_T1_ENABLE = 0b0000_1000
        .const TMR_T1_LOAD = 0b0000_0100
        .const TMR_T0_DISABLE = 0b0000_0000
        .const TMR_T0_ENABLE = 0b0000_0010
        .const TMR_T0_LOAD = 0b0000_0001

        .const P3M_PARITY_OFF = 0b0000_0000
        .const P3M_PARITY_ON = 0b1000_0000
        .const P3M_P30_P37_IO = 0b0000_0000
        .const P3M_P30_P37_SERIAL = 0b0100_0000
        .const P3M_P31_P36_IO = 0b0000_0000
        .const P3M_P31_P36_HANDSHAKE_P2 = 0b0010_0000
        .const P3M_P33_P34_IO = 0b0000_0000
        .const P3M_P33_P34_INPUT_DM = 0b0001_0000
        .const P3M_P33_P34_INPUT_DM2 = 0b0000_1000
        .const P3M_P33_P34_HANDSHAKE_P1 = 0b0001_1000
        .const P3M_P32_P35_IO = 0b0000_0000
        .const P3M_P32_P35_HANDSHAKE_P0 = 0b0000_0100
        .const P3M_P2_OPEN_DRAIN = 0b0000_0000
        .const P3M_P2_PULLUP = 0b0000_0001

        .const P01M_P04_P07_OUTPUT = 0b0000_0000
        .const P01M_P04_P07_INPUT = 0b0100_0000
        .const P01M_P04_P07_A12_A15 = 0b1000_0000
        .const P01M_MEM_TIMING_NORMAL = 0b0000_0000
        .const P01M_MEM_TIMING_EXTENDED = 0b0010_0000
        .const P01M_P10_P17_OUTPUT = 0b0000_0000
        .const P01M_P10_P17_AD0_AD7 = 0b0001_0000
        .const P01M_P10_P17_TRISTATE = 0b0001_1000
        .const P01M_STACK_EXTERN = 0b0000_0000
        .const P01M_STACK_INTERN = 0b0000_0100
        .const P01M_P00_P03_OUTPUT = 0b0000_0000
        .const P01M_P00_P03_INPUT = 0b0000_0001
        .const P01M_P00_P03_A8_A11 = 0b0000_0010

        .repeat 6
            NOP
            NOP
            NOP
        .end

        DI
        SRP     #%F0
        LD      R8, #%B2    ; P01M = P01M_P00_P03_A8_A11
                            ;      | P01M_STACK_EXTERN
                            ;      | P01M_P10_P17_AD0_AD7
                            ;      | P01M_MEM_TIMING_EXTENDED
                            ;      | P01M_P04_P07_A12_A15
        LD      R7, #9      ; P3M = P3M_P30_P37_IO
                            ;     | P3M_P31_P36_IO
                            ;     | P3M_P33_P34_INPUT_DM2
                            ;     | P3M_P2_PULLUP
        LD      R6, #%C0    ; P20-25 = output, P26-27 = input
        LD      R1, #0      ; TMR = TMR_P36_NOT_USED
                            ;     | TMR_P31_AS_PRESCALER_SOURCE
                            ;     | TMR_T1_DISABLE
                            ;     | TMR_T0_DISABLE
        NOP
        NOP
        NOP
        SRP     #0
        .repeat 216
            NOP
        .end
        CP      R10, R2
M_08FC:
        LD      R3, #%F0    ; Shift = 1, BUSY = 1, /SYN = 0
        .repeat 19
            CP      R10, R2
        .end
        LD      R3, #%70    ; Shift = 0
        CP      R10, R2
        CP      R10, R2
        CP      R10, R2
        LD      R3, #%50    ; /SYN = 0 
        CP      R10, R2
        LD      R3, #%70    ; /SYN = 1
        CP      R10, R2
        CP      R10, R2
        JP      %DA22       ; M_0A22
        .repeat 233
            NOP
        .end
M_0A22:
        CP      R10, R2
        LD      R3, #%F0    ; Shift = 1, BUSY = 1, /SYN = 0
        .repeat 19
            CP      R10, R2
        .end
        LD      R3, #%70    ; Shift = 0
        .repeat 3
            CP      R10, R2
        .end
        LD      R3, #%50    ; /SYN = 0
        CP      R10, R2
        LD      R3, #%70    ; /SYN = 1
        CP      R10, R2
        CP      R10, R2
        JP      %DB4A       ; M_0B4A
        .repeat 233
            NOP
        .end
M_0B4A:
        CP      R10, R2
        LD      R3, #%F0    ; Shift = 1, BUSY = 1, /SYN = 0
        .repeat 19
            CP      R10, R2
        .end
        LD      R3, #%70    ; Shift = 0
        .repeat 3
            CP      R10, R2
        .end
        LD      R3, #%50    ; /SYN = 0
        LD      R14, #55
        LD      R3, #%70    ; /SYN = 1
        INC     %2
        DEC     %0D
        OR      R13, R13
        JP      NZ, %D8FC   ; %08FC
        JR      M_0B8D
M_0B8D: JR      M_0B8F
M_0B8F: JR      M_0B9B
M_0B91:
        .repeat 5
            CP      R10, R2
        .end
M_0B9B: LD      R3, #%30    ; BUSY = 0
        .repeat 18
            CP      R10, R2
        .end
        LD      R3, #%10    ; /SYN = 0
        CP      R10, R2
        LD      R3, #%30    ; /SYN = 1
        LD      R15, #10
        DEC     %0E
        OR      R14, R14
        JP      NZ, %DB91   ; M_0B91
        JR      M_0BD2
M_0BD2: JR      M_0BD4
M_0BD4: JR      M_0BE0
M_0BD6:
        .repeat 5
            CP      R10, R2
        .end
M_0BE0: .repeat 17
            CP      R10, R2
        .end
        LD      R3, #%30    ; /SYN = 1
        CP      R10, R2
        LD      R3, #%10    ; /SYN = 0
        CP      R10, R2
        LD      R3, #%10    ; /SYN = 0
        LD      R14, #57
        DEC     %0F
        OR      R15, R15
        JP      NZ, %DBD6   ; M_0BD6
        JR      M_0C17
M_0C17: JR      M_0C19
M_0C19: JR      M_0C25
        .repeat 5
            CP      R10, R2
        .end
M_0C25: .repeat 11
            CP      R10, R2
        .end
        LD      R4, #4      ; { if R14 < #4
        CP      R14, R4     ;     BUSY = 1
        LD      R4, #0
        RRC     %4
        RRC     %4
        OR      R3, R4      ; }
        LD      R5, #0b1101_0000
        LD      R6, #0b0010_0000
        AND     R3, R5      ; /SYN = 0
        LD      R2, #0
        OR      R3, R6      ; /SYN = 1
        LD      R13, #%40   ; 64
        DEC     %0E
        OR      R14, R14
        JP      Z, %D8FC    ; M_08FC
        JR      M_0C17

        .repeat 932
            NOP
        .end
