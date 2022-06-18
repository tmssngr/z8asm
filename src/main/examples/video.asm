        .ORG    %0800

        .repeat 6
            NOP
            NOP
            NOP
        .end

        DI
        SRP     #%F0
        LD      R8, #%B2
        LD      R7, #9
        LD      R6, #%C0
        LD      R1, #0
        NOP
        NOP
        NOP
        SRP     #0
        .repeat 216
            NOP
        .end
        CP      R10, R2
        LD      R3, #%F0
        .repeat 19
            CP      R10, R2
        .end
        LD      R3, #%70
        CP      R10, R2
        CP      R10, R2
        CP      R10, R2
        LD      R3, #%50
        CP      R10, R2
        LD      R3, #%70
        CP      R10, R2
        CP      R10, R2
        JP      %DA22
        .repeat 233
            NOP
        .end
        CP      R10, R2
        LD      R3, #%F0
        .repeat 19
            CP      R10, R2
        .end
        LD      R3, #%70
        CP      R10, R2
        CP      R10, R2
        CP      R10, R2
        LD      R3, #%50
        CP      R10, R2
        LD      R3, #%70
        CP      R10, R2
        CP      R10, R2
        JP      %DB4A
        .repeat 233
            NOP
        .end
        CP      R10, R2
        LD      R3, #%F0
        .repeat 19
            CP      R10, R2
        .end
        LD      R3, #%70
        .repeat 3
            CP      R10, R2
        .end
        LD      R3, #%50
        LD      R14, #%37
        LD      R3, #%70
        INC     %2
        DEC     %0D
        OR      R13, R13
        JP      NZ, %D8FC
        JR      M_0B8D
M_0B8D: JR      M_0B8F
M_0B8F: JR      M_0B9B
        .repeat 5
            CP      R10, R2
        .end
M_0B9B: LD      R3, #%30
        .repeat 18
            CP      R10, R2
        .end
        LD      R3, #%10
        CP      R10, R2
        LD      R3, #%30
        LD      R15, #%0A
        DEC     %0E
        OR      R14, R14
        JP      NZ, %DB91
        JR      M_0BD2
M_0BD2: JR      M_0BD4
M_0BD4: JR      M_0BE0
        .repeat 5
            CP      R10, R2
        .end
M_0BE0: .repeat 17
            CP      R10, R2
        .end
        LD      R3, #%30
        CP      R10, R2
        LD      R3, #%10
        CP      R10, R2
        LD      R3, #%10
        LD      R14, #%39
        DEC     %0F
        OR      R15, R15
        JP      NZ, %DBD6
        JR      M_0C17
M_0C17: JR      M_0C19
M_0C19: JR      M_0C25
        .repeat 5
            CP      R10, R2
        .end
M_0C25: .repeat 11
            CP      R10, R2
        .end
        LD      R4, #4
        CP      R14, R4
        LD      R4, #0
        RRC     %4
        RRC     %4
        OR      R3, R4
        LD      R5, #%D0
        LD      R6, #%20
        AND     R3, R5
        LD      R2, #0
        OR      R3, R6
        LD      R13, #%40
        DEC     %0E
        OR      R14, R14
        JP      Z, %D8FC
        JR      M_0C17

        .repeat 932
            NOP
        .end
