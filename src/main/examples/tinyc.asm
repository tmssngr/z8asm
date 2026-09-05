        .const  RP    = %FD
        .const  SPH   = %FE
        .const  SPL   = %FF

        .org %e000

start:
        srp  #%20
        jr   main

        ; i16 tileValue@u8
        ; arg exponent (u8): r0
tileValue_Pu8:
        ; const value.1{r2}, 1
        ld   r2, #%00
        ld   r3, #%01
        ; const index.1{r4}, 0
        ld   r4, #%00
        ; 47:2 for index < exponent
        jr   for__1

for__1__body:
        ; shiftleft value.3{r2}, 1
        add r3, r3
        adc r2, r2
        ; add index.3{r4}, 1
        inc  r4
for__1:
        ; branch index.2{r4} lt param.exponent{r0}: for_1_body
        cp   r4, r0
        jr   ult, for__1__body
        ; 50:9 return value
        ; move value.2{r0}, value.2{r2}
        ld   r0, r2
        ld   r1, r3
        ret

        ; void spawnTile
spawnTile:
        ; save clobbered non-volatile registers
        push r8
        push r9
        push r10
        push r11
        push r12
        push r13
        ; const emptyCount.1{r8}, 0
        ld   r8, #%00
        ; const index.1{r9}, 0
        ld   r9, #%00
        ; 56:2 for index < 16
        jr   for__2

for__2__body:
        ; 57:3 if [...] == 0
        ; cast t.7.1{r10}(i16), index.2{r9}(u8)
        ld   r11, r9
        ld   r10, #0
        ; addrof t.6.1{r12}, tiles
        ld   r12, #hi(var_0)
        ld   r13, #lo(var_0)
        ; add t.6.2{r12}, t.7.1{r10}
        add  r13, r11
        adc  r12, r10
        ; load t.5.1{r10}, [t.6.2{r12}]
        lde  r10, @rr12
        ; branch t.5.1{r10} notequals 0: for_2_continue
        cp   r10, #%00
        jr   ne, for__2__continue
        ; add emptyCount.4{r8}, 1
        inc  r8
for__2__continue:
        ; add index.6{r9}, 1
        inc  r9
for__2:
        ; branch index.2{r9} lt 16: for_2_body
        cp   r9, #%10
        jr   ult, for__2__body
        ; branch emptyCount.2{r8} equals 0: spawnTile_ret
        cp   r8, #%00
        jr   eq, spawnTile__ret
        ; call t.9.1{r0} = random16[] -> i16
        call random16
        ; cast t.10.1{r8}(i16), emptyCount.2{r8}(u8)
        ld   r9, r8
        ld   r8, #0
        ; move t.8.1{r10}, t.9.1{r0}
        ld   r11, r1
        ld   r10, r0
        ; mod t.8.1{r10}, t.10.1{r8}
        ld   %12, r10
        ld   %13, r11
        ld   %14, r8
        ld   %15, r9
        srp  #%10
        call %011F ; mod
        srp  #%20
        ld   r10, %12
        ld   r11, %13
        ; cast chosen.1{r8}(u8), t.8.1{r10}(i16)
        ld   r8, r11
        ; const index.3{r9}, 0
        ld   r9, #%00
        ; 69:2 for index < 16
        jr   for__5

for__5__body:
        ; 70:3 if [...] != 0
        ; cast t.13.1{r10}(i16), index.4{r9}(u8)
        ld   r11, r9
        ld   r10, #0
        ; addrof t.12.1{r12}, tiles
        ld   r12, #hi(var_0)
        ld   r13, #lo(var_0)
        ; add t.12.2{r12}, t.13.1{r10}
        add  r13, r11
        adc  r12, r10
        ; load t.11.1{r10}, [t.12.2{r12}]
        lde  r10, @rr12
        ; branch t.11.1{r10} notequals 0: for_5_continue
        cp   r10, #%00
        jr   ne, for__5__continue
        ; branch chosen.2{r8} equals 0: if_7_then
        cp   r8, #%00
        jr   eq, if__7__then
        ; sub chosen.4{r8}, 1
        dec  r8
for__5__continue:
        ; add index.8{r9}, 1
        inc  r9
for__5:
        ; branch index.4{r9} gteq 16: spawnTile_ret, for_5_body
        cp   r9, #%10
        jr   uge, spawnTile__ret
        jr   for__5__body

if__7__then:
        ; const tile.1{r8}, 1
        ld   r8, #%01
        ; 75:4 if random16([]) % 10 == 0
        ; call t.15.1{r0} = random16[] -> i16
        call random16
        ; mod t.14.1{r0}, 10
        ld   %12, r0
        ld   %13, r1
        ld   %14, #%00
        ld   %15, #%0a
        srp  #%10
        call %011F ; mod
        srp  #%20
        ld   r0, %12
        ld   r1, %13
        ; branch t.14.1{r0} equals 0: if_8_then
        cp   r1, #%00
        jr   ne, .notEquals1
        cp   r0, #%00
        jr   eq, if__8__then
.notEquals1:
        ; move tile.2{r0}, tile.1{r8}
        ld   r0, r8
        jr   if__8__end

if__8__then:
        ; const tile.3{r0}, 2
        ld   r0, #%02
if__8__end:
        ; cast t.17.1{r1}(i16), index.4{r9}(u8)
        ld   r2, r9
        ld   r1, #0
        ; addrof t.16.1{r4}, tiles
        ld   r4, #hi(var_0)
        ld   r5, #lo(var_0)
        ; add t.16.2{r4}, t.17.1{r1}
        add  r5, r2
        adc  r4, r1
        ; store [t.16.2{r4}], tile.2{r0}
        lde  @rr4, r0
        ; 79:4 return
spawnTile__ret:
        ; restore clobbered non-volatile registers
        pop  r13
        pop  r12
        pop  r11
        pop  r10
        pop  r9
        pop  r8
        ret

        ; bool slideLine@u8@u8
        ; arg first (u8): r0
        ; arg step (u8): r1
        ; var source.2 (u8): SP+8
        ; var target.2 (u8): SP+9
        ; var position.2 (u8): SP+10
        ; var tile.1 (u8): SP+11
        ; var merged.2 (bool): SP+12
        ; var param.first (u8): SP+13
        ; var param.step (u8): SP+14
slideLine_Pu8_Pu8:
        ld   %30, SPH
        ld   %31, SPL
        sub  %31, #%07
        sbc  %30, #%00
        ld   SPH, %30
        ld   SPL, %31
        ; save clobbered non-volatile registers
        push r8
        push r9
        push r10
        push r11
        push r12
        push r13
        push r14
        push r15
        ; move param.first{r8}, first{r0}
        ld   r8, r0
        ; move param.step{r9}, step{r1}
        ld   r9, r1
        ; move source.1{r10}, param.first{r8}
        ld   r10, r8
        ; move target.1{r11}, param.first{r8}
        ld   r11, r8
        ; const previousMerged.1{r12}, 0
        ld   r12, #%00
        ; const moved.1{r13}, 0
        ld   r13, #%00
        ; const position.1{r1}, 0
        ld   r1, #%00
        ; 93:2 for position < 4
        ; addrof memVarAddr{r14}, position.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0a
        adc  r14, #%00
        ; store [memVarAddr{r14}], position.2{r1}
        lde  @rr14, r1
        ; addrof memVarAddr{r14}, param.first
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0d
        adc  r14, #%00
        ; store [memVarAddr{r14}], param.first{r8}
        lde  @rr14, r8
        ; move param.step{r4}, param.step{r9}
        ld   r4, r9
        ; addrof memVarAddr{r14}, position.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0a
        adc  r14, #%00
        ; move position.2{r3}, position.2{r1}
        ld   r3, r1
        ; move source.2{r2}, source.2{r10}
        ld   r2, r10
        ; move target.2{r1}, target.2{r11}
        ld   r1, r11
        jr   for__9

for__9__body:
        ; addrof memVarAddr{r14}, position.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0a
        adc  r14, #%00
        ; store [memVarAddr{r14}], position.2{r3}
        lde  @rr14, r3
        ; addrof memVarAddr{r14}, param.first
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0d
        adc  r14, #%00
        ; load param.first{r8}, [memVarAddr{r14}]
        lde  r8, @rr14
        ; move param.step{r9}, param.step{r4}
        ld   r9, r4
        ; move source.2{r10}, source.2{r2}
        ld   r10, r2
        ; move target.2{r11}, target.2{r1}
        ld   r11, r1
        ; cast t.14.1{r1}(i16), source.2{r10}(u8)
        ld   r2, r10
        ld   r1, #0
        ; addrof sourceTile.1{r4}, tiles
        ld   r4, #hi(var_0)
        ld   r5, #lo(var_0)
        ; add sourceTile.2{r4}, t.14.1{r1}
        add  r5, r2
        adc  r4, r1
        ; load tile.1{r1}, [sourceTile.2{r4}]
        lde  r1, @rr4
        ; 96:3 if tile != 0
        ; branch tile.1{r1} notequals 0: if_10_then
        cp   r1, #%00
        jr   ne, if__10__then
        ; move param.step{r4}, param.step{r9}
        ld   r4, r9
        ; move source.2{r2}, source.2{r10}
        ld   r2, r10
        jr   if__10__end

if__10__then:
        ; const t.15.1{r2}, 0
        ld   r2, #%00
        ; store [sourceTile.2{r4}], t.15.1{r2}
        lde  @rr4, r2
        ; const merged.1{r2}, 0
        ld   r2, #%00
        ; 102:4 if target != first && !previousMerged
        ; branch target.2{r11} notequals param.first{r8}: and_12
        cp   r11, r8
        jr   ne, and__12
        ; addrof memVarAddr{r14}, param.first
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0d
        adc  r14, #%00
        ; store [memVarAddr{r14}], param.first{r8}
        lde  @rr14, r8
        ; addrof memVarAddr{r14}, param.step
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0e
        adc  r14, #%00
        ; store [memVarAddr{r14}], param.step{r9}
        lde  @rr14, r9
        ; addrof memVarAddr{r14}, source.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; store [memVarAddr{r14}], source.2{r10}
        lde  @rr14, r10
        ; addrof memVarAddr{r14}, target.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%09
        adc  r14, #%00
        ; store [memVarAddr{r14}], target.2{r11}
        lde  @rr14, r11
        ; addrof memVarAddr{r14}, tile.1
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0b
        adc  r14, #%00
        ; store [memVarAddr{r14}], tile.1{r1}
        lde  @rr14, r1
        ; move merged.2{r1}, merged.2{r2}
        ld   r1, r2
        jr   if__11__end

and__12:
        ; branch previousMerged.2{r12} equals 0: if_11_then
        cp   r12, #%00
        jr   eq, if__11__then
        ; addrof memVarAddr{r14}, param.first
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0d
        adc  r14, #%00
        ; store [memVarAddr{r14}], param.first{r8}
        lde  @rr14, r8
        ; addrof memVarAddr{r14}, param.step
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0e
        adc  r14, #%00
        ; store [memVarAddr{r14}], param.step{r9}
        lde  @rr14, r9
        ; addrof memVarAddr{r14}, source.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; store [memVarAddr{r14}], source.2{r10}
        lde  @rr14, r10
        ; addrof memVarAddr{r14}, target.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%09
        adc  r14, #%00
        ; store [memVarAddr{r14}], target.2{r11}
        lde  @rr14, r11
        ; addrof memVarAddr{r14}, tile.1
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0b
        adc  r14, #%00
        ; store [memVarAddr{r14}], tile.1{r1}
        lde  @rr14, r1
        ; move merged.2{r1}, merged.2{r2}
        ld   r1, r2
        jr   if__11__end

if__11__then:
        ; move previous.1{r3}, target.2{r11}
        ld   r3, r11
        ; sub previous.1{r3}, param.step{r9}
        sub  r3, r9
        ; cast t.16.1{r4}(i16), previous.1{r3}(u8)
        ld   r5, r3
        ld   r4, #0
        ; addrof previousTile.1{r6}, tiles
        ld   r6, #hi(var_0)
        ld   r7, #lo(var_0)
        ; add previousTile.2{r6}, t.16.1{r4}
        add  r7, r5
        adc  r6, r4
        ; 105:5 if *previousTile == tile
        ; load t.17.1{r4}, [previousTile.2{r6}]
        lde  r4, @rr6
        ; branch t.17.1{r4} equals tile.1{r1}: if_13_then
        cp   r4, r1
        jr   eq, if__13__then
        ; addrof memVarAddr{r14}, merged.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0c
        adc  r14, #%00
        ; store [memVarAddr{r14}], merged.2{r2}
        lde  @rr14, r2
        ; addrof memVarAddr{r14}, param.first
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0d
        adc  r14, #%00
        ; store [memVarAddr{r14}], param.first{r8}
        lde  @rr14, r8
        ; addrof memVarAddr{r14}, param.step
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0e
        adc  r14, #%00
        ; store [memVarAddr{r14}], param.step{r9}
        lde  @rr14, r9
        ; addrof memVarAddr{r14}, source.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; store [memVarAddr{r14}], source.2{r10}
        lde  @rr14, r10
        ; addrof memVarAddr{r14}, target.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%09
        adc  r14, #%00
        ; store [memVarAddr{r14}], target.2{r11}
        lde  @rr14, r11
        ; addrof memVarAddr{r14}, tile.1
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0b
        adc  r14, #%00
        ; store [memVarAddr{r14}], tile.1{r1}
        lde  @rr14, r1
        ; addrof memVarAddr{r14}, merged.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0c
        adc  r14, #%00
        ; move merged.2{r1}, merged.2{r2}
        ld   r1, r2
        jr   if__11__end

if__13__then:
        ; cast t.19.1{r12}(i16), source.2{r10}(u8)
        ld   r13, r10
        ld   r12, #0
        ; addrof t.18.1{r4}, moveTargets
        ld   r4, #hi(var_2)
        ld   r5, #lo(var_2)
        ; add t.18.2{r4}, t.19.1{r12}
        add  r5, r13
        adc  r4, r12
        ; store [t.18.2{r4}], previous.1{r3}
        lde  @rr4, r3
        ; move nextTile.1{r12}, tile.1{r1}
        ld   r12, r1
        ; addrof memVarAddr{r14}, tile.1
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0b
        adc  r14, #%00
        ; store [memVarAddr{r14}], tile.1{r1}
        lde  @rr14, r1
        ; add nextTile.1{r12}, 1
        inc  r12
        ; store [previousTile.2{r6}], nextTile.1{r12}
        lde  @rr6, r12
        ; move nextTile.1{r0}, nextTile.1{r12}
        ld   r0, r12
        ; call t.20.1{r0} = tileValue@u8[nextTile.1{r0}] -> i16
        call tileValue_Pu8
        ; cast mergedScore.1{r1}(i32), t.20.1{r0}(i16)
        ld   r4, r1
        ld   r3, r0
        ld   r2, r3
        rl   r2
        sbc  r2, r2
        sbc  r1, r1
        ; addrof a.22.1{r6}, score
        ld   r6, #hi(var_5)
        ld   r7, #lo(var_5)
        ; addrof memVarAddr{r14}, param.first
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0d
        adc  r14, #%00
        ; store [memVarAddr{r14}], param.first{r8}
        lde  @rr14, r8
        ; addrof memVarAddr{r14}, param.step
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0e
        adc  r14, #%00
        ; store [memVarAddr{r14}], param.step{r9}
        lde  @rr14, r9
        ; addrof memVarAddr{r14}, source.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; store [memVarAddr{r14}], source.2{r10}
        lde  @rr14, r10
        ; addrof memVarAddr{r14}, target.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%09
        adc  r14, #%00
        ; store [memVarAddr{r14}], target.2{r11}
        lde  @rr14, r11
        ; load t.23.1{r8}, [a.22.1{r6}]
        lde  r8, @rr6
        incw r6
        lde  r9, @rr6
        incw r6
        lde  r10, @rr6
        incw r6
        lde  r11, @rr6
        sub  r7, #3
        sbc  r6, #0
        ; move t.21.1{r5}, t.23.1{r8}
        ld   r5, r8
        ld   r6, r9
        ld   r7, r10
        ld   r8, r11
        ; add t.21.1{r5}, mergedScore.1{r1}
        add  r8, r4
        adc  r7, r3
        adc  r6, r2
        adc  r5, r1
        ; addrof a.24.1{r2}, score
        ld   r2, #hi(var_5)
        ld   r3, #lo(var_5)
        ; store [a.24.1{r2}], t.21.1{r5}
        lde  @rr2, r5
        incw r2
        lde  @rr2, r6
        incw r2
        lde  @rr2, r7
        incw r2
        lde  @rr2, r8
        sub  r3, #3
        sbc  r2, #0
        ; 112:6 if nextTile == 11
        ; branch nextTile.1{r12} notequals 11: if_14_end
        cp   r12, #%0b
        jr   ne, if__14__end
        ; const t.25.1{r1}, 1
        ld   r1, #%01
        ; addrof a.26.1{r2}, gameState
        ld   r2, #hi(var_6)
        ld   r3, #lo(var_6)
        ; store [a.26.1{r2}], t.25.1{r1}
        lde  @rr2, r1
if__14__end:
        ; const merged.3{r1}, 1
        ld   r1, #%01
        ; const previousMerged.6{r2}, 1
        ld   r2, #%01
        ; const moved.7{r3}, 1
        ld   r3, #%01
        ; move previousMerged.4{r12}, previousMerged.6{r2}
        ld   r12, r2
        ; move moved.4{r13}, moved.7{r3}
        ld   r13, r3
if__11__end:
        ; branch merged.2{r1} equals 0: if_15_then
        cp   r1, #%00
        jr   eq, if__15__then
        ; addrof memVarAddr{r14}, target.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%09
        adc  r14, #%00
        ; load target.2{r1}, [memVarAddr{r14}]
        lde  r1, @rr14
        ; move target.3{r11}, target.2{r1}
        ld   r11, r1
        ; addrof memVarAddr{r14}, param.step
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0e
        adc  r14, #%00
        ; load param.step{r4}, [memVarAddr{r14}]
        lde  r4, @rr14
        ; addrof memVarAddr{r14}, source.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; load source.2{r2}, [memVarAddr{r14}]
        lde  r2, @rr14
        jr   if__10__end

if__15__then:
        ; addrof memVarAddr{r14}, target.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%09
        adc  r14, #%00
        ; load target.2{r1}, [memVarAddr{r14}]
        lde  r1, @rr14
        ; addrof memVarAddr{r14}, source.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; load source.2{r2}, [memVarAddr{r14}]
        lde  r2, @rr14
        ; cast t.28.1{r3}(i16), source.2{r2}(u8)
        ld   r4, r2
        ld   r3, #0
        ; addrof t.27.1{r6}, moveTargets
        ld   r6, #hi(var_2)
        ld   r7, #lo(var_2)
        ; add t.27.2{r6}, t.28.1{r3}
        add  r7, r4
        adc  r6, r3
        ; store [t.27.2{r6}], target.2{r1}
        lde  @rr6, r1
        ; cast t.30.1{r3}(i16), target.2{r1}(u8)
        ld   r4, r1
        ld   r3, #0
        ; addrof t.29.1{r6}, tiles
        ld   r6, #hi(var_0)
        ld   r7, #lo(var_0)
        ; add t.29.2{r6}, t.30.1{r3}
        add  r7, r4
        adc  r6, r3
        ; addrof memVarAddr{r14}, tile.1
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0b
        adc  r14, #%00
        ; load tile.1{r3}, [memVarAddr{r14}]
        lde  r3, @rr14
        ; store [t.29.2{r6}], tile.1{r3}
        lde  @rr6, r3
        ; 124:5 if target != source
        ; branch target.2{r1} notequals source.2{r2}: if_16_then
        cp   r1, r2
        jr   ne, if__16__then
        ; move moved.5{r3}, moved.4{r13}
        ld   r3, r13
        jr   if__16__end

if__16__then:
        ; const moved.6{r3}, 1
        ld   r3, #%01
if__16__end:
        ; addrof memVarAddr{r14}, param.step
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0e
        adc  r14, #%00
        ; load param.step{r4}, [memVarAddr{r14}]
        lde  r4, @rr14
        ; add target.7{r1}, param.step{r4}
        add  r1, r4
        ; const previousMerged.5{r5}, 0
        ld   r5, #%00
        ; move target.3{r11}, target.7{r1}
        ld   r11, r1
        ; move previousMerged.3{r12}, previousMerged.5{r5}
        ld   r12, r5
        ; move moved.3{r13}, moved.5{r3}
        ld   r13, r3
if__10__end:
        ; move source.4{r1}, source.2{r2}
        ld   r1, r2
        ; add source.4{r1}, param.step{r4}
        add  r1, r4
        ; addrof memVarAddr{r14}, position.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%0a
        adc  r14, #%00
        ; load position.2{r3}, [memVarAddr{r14}]
        lde  r3, @rr14
        ; add position.5{r3}, 1
        inc  r3
        ; move source.2{r2}, source.4{r1}
        ld   r2, r1
        ; move target.2{r1}, target.3{r11}
        ld   r1, r11
for__9:
        ; branch position.2{r3} lt 4: for_9_body
        cp   r3, #%04
        jr   ult, for__9__body
        ; 133:9 return moved
        ; move moved.2{r0}, moved.2{r13}
        ld   r0, r13
        ; restore clobbered non-volatile registers
        pop  r15
        pop  r14
        pop  r13
        pop  r12
        pop  r11
        pop  r10
        pop  r9
        pop  r8
        ld   %30, SPH
        ld   %31, SPL
        add  %31, #%07
        adc  %30, #%00
        ld   SPL, %31
        ld   SPH, %30
        ret

        ; bool slide@u8
        ; arg direction (u8): r0
slide_Pu8:
        ; save clobbered non-volatile registers
        push r8
        push r9
        push r10
        push r11
        push r12
        push r13
        ; move param.direction{r8}, direction{r0}
        ld   r8, r0
        ; const index.1{r9}, 0
        ld   r9, #%00
        ; 139:2 for index < 16
        jr   for__17

for__17__body:
        ; cast t.8.1{r10}(i16), index.2{r9}(u8)
        ld   r11, r9
        ld   r10, #0
        ; addrof t.7.1{r12}, tiles
        ld   r12, #hi(var_0)
        ld   r13, #lo(var_0)
        ; add t.7.2{r12}, t.8.1{r10}
        add  r13, r11
        adc  r12, r10
        ; load t.6.1{r10}, [t.7.2{r12}]
        lde  r10, @rr12
        ; cast t.10.1{r11}(i16), index.2{r9}(u8)
        ld   r12, r9
        ld   r11, #0
        ; addrof t.9.1{r2}, moveTiles
        ld   r2, #hi(var_1)
        ld   r3, #lo(var_1)
        ; add t.9.2{r2}, t.10.1{r11}
        add  r3, r12
        adc  r2, r11
        ; store [t.9.2{r2}], t.6.1{r10}
        lde  @rr2, r10
        ; move t.12.1{r10}, t.12.1{r11}
        ld   r10, r11
        ld   r11, r12
        ; addrof t.11.1{r12}, moveTargets
        ld   r12, #hi(var_2)
        ld   r13, #lo(var_2)
        ; add t.11.2{r12}, t.12.1{r10}
        add  r13, r11
        adc  r12, r10
        ; store [t.11.2{r12}], index.2{r9}
        lde  @rr12, r9
        ; add index.3{r9}, 1
        inc  r9
for__17:
        ; branch index.2{r9} lt 16: for_17_body
        cp   r9, #%10
        jr   ult, for__17__body
        ; const moved.1{r9}, 0
        ld   r9, #%00
        ; const line.1{r10}, 0
        ld   r10, #%00
        ; 144:2 for line < 4
        jr   for__18

for__18__body:
        ; move first.1{r11}, line.2{r10}
        ld   r11, r10
        ; shiftleft first.1{r11}, 2
        add r11, r11
        add r11, r11
        ; const step.1{r12}, 1
        ld   r12, #%01
        ; 150:3 if direction == 1
        ; branch param.direction{r8} equals 1: if_19_then
        cp   r8, #%01
        jr   eq, if__19__then
        ; branch param.direction{r8} notequals 2: if_20_else, if_20_then
        cp   r8, #%02
        jr   ne, if__20__else
        jr   if__20__then

if__19__then:
        ; add first.2{r11}, 3
        add  r11, #%03
        ; const step.2{r12}, 255
        ld   r12, #%ff
        ; move first.3{r0}, first.2{r11}
        ld   r0, r11
        ; move step.3{r1}, step.2{r12}
        ld   r1, r12
        jr   if__19__end

if__20__else:
        ; branch param.direction{r8} notequals 3: slide@u8.no_critical_edge_17, if_21_then
        cp   r8, #%03
        jr   ne, slide_Pu8_2eno__critical__edge__17
        jr   if__21__then

if__20__then:
        ; move first.4{r11}, line.2{r10}
        ld   r11, r10
        ; const step.4{r12}, 4
        ld   r12, #%04
        ; move first.3{r0}, first.4{r11}
        ld   r0, r11
        ; move step.3{r1}, step.4{r12}
        ld   r1, r12
        jr   if__19__end

slide_Pu8_2eno__critical__edge__17:
        ; move first.3{r0}, first.1{r11}
        ld   r0, r11
        ; move step.3{r1}, step.1{r12}
        ld   r1, r12
        jr   if__19__end

if__21__then:
        ; move first.5{r11}, line.2{r10}
        ld   r11, r10
        ; add first.5{r11}, 12
        add  r11, #%0c
        ; const step.5{r12}, 252
        ld   r12, #%fc
        ; move first.3{r0}, first.5{r11}
        ld   r0, r11
        ; move step.3{r1}, step.5{r12}
        ld   r1, r12
if__19__end:
        ; 164:3 if slideLine@u8@u8([ExprVarAccess[varName=first, index=4, scope=function, type=u8, varIsArray=false, location=164:16], ExprVarAccess[varName=step, index=5, scope=function, type=u8, varIsArray=false, location=164:23]])
        ; call t.13.1{r0} = slideLine@u8@u8[first.3{r0}, step.3{r1}] -> bool
        call slideLine_Pu8_Pu8
        ; branch t.13.1{r0} notequals 0: if_22_then
        cp   r0, #%00
        jr   ne, if__22__then
        ; move moved.4{r1}, moved.2{r9}
        ld   r1, r9
        jr   for__18__continue

if__22__then:
        ; const moved.5{r1}, 1
        ld   r1, #%01
for__18__continue:
        ; move line.5{r2}, line.2{r10}
        ld   r2, r10
        ; add line.5{r2}, 1
        inc  r2
        ; move moved.2{r9}, moved.4{r1}
        ld   r9, r1
        ; move line.2{r10}, line.5{r2}
        ld   r10, r2
for__18:
        ; branch line.2{r10} lt 4: for_18_body
        cp   r10, #%04
        jr   ult, for__18__body
        ; 168:9 return moved
        ; move moved.2{r0}, moved.2{r9}
        ld   r0, r9
        ; restore clobbered non-volatile registers
        pop  r13
        pop  r12
        pop  r11
        pop  r10
        pop  r9
        pop  r8
        ret

        ; bool canMove
canMove:
        ; const index.1{r1}, 0
        ld   r1, #%00
        ; 173:2 for index < 16
        jr   for__23

for__23__body:
        ; cast t.3.1{r2}(i16), index.2{r1}(u8)
        ld   r3, r1
        ld   r2, #0
        ; addrof t.2.1{r4}, tiles
        ld   r4, #hi(var_0)
        ld   r5, #lo(var_0)
        ; add t.2.2{r4}, t.3.1{r2}
        add  r5, r3
        adc  r4, r2
        ; load tile.1{r2}, [t.2.2{r4}]
        lde  r2, @rr4
        ; 175:3 if tile == 0
        ; branch tile.1{r2} equals 0: if_24_then
        cp   r2, #%00
        jr   eq, if__24__then
        ; 180:3 if index & 3 != 3 && [...] == tile
        ; move t.4.1{r3}, index.2{r1}
        ld   r3, r1
        ; and t.4.1{r3}, 3
        and  r3, #%03
        ; branch t.4.1{r3} equals 3: if_25_end
        cp   r3, #%03
        jr   eq, if__25__end
        ; move t.8.1{r3}, index.2{r1}
        ld   r3, r1
        ; add t.8.1{r3}, 1
        inc  r3
        ; cast t.7.1{r3}(i16), t.8.1{r3}(u8)
        ld   r4, r3
        ld   r3, #0
        ; addrof t.6.1{r6}, tiles
        ld   r6, #hi(var_0)
        ld   r7, #lo(var_0)
        ; add t.6.2{r6}, t.7.1{r3}
        add  r7, r4
        adc  r6, r3
        ; load t.5.1{r3}, [t.6.2{r6}]
        lde  r3, @rr6
        ; branch t.5.1{r3} equals tile.1{r2}: if_25_then, if_25_end
        cp   r3, r2
        jr   eq, if__25__then
if__25__end:
        ; branch index.2{r1} gteq 12: for_23_continue
        cp   r1, #%0c
        jr   uge, for__23__continue
        ; move t.12.1{r3}, index.2{r1}
        ld   r3, r1
        ; add t.12.1{r3}, 4
        add  r3, #%04
        ; cast t.11.1{r3}(i16), t.12.1{r3}(u8)
        ld   r4, r3
        ld   r3, #0
        ; addrof t.10.1{r6}, tiles
        ld   r6, #hi(var_0)
        ld   r7, #lo(var_0)
        ; add t.10.2{r6}, t.11.1{r3}
        add  r7, r4
        adc  r6, r3
        ; load t.9.1{r3}, [t.10.2{r6}]
        lde  r3, @rr6
        ; branch t.9.1{r3} equals tile.1{r2}: if_27_then, for_23_continue
        cp   r3, r2
        jr   eq, if__27__then
for__23__continue:
        ; add index.5{r1}, 1
        inc  r1
for__23:
        ; branch index.2{r1} lt 16: for_23_body
        cp   r1, #%10
        jr   ult, for__23__body
        ; const {r0}, 0
        ld   r0, #%00
        jr   canMove__ret

if__24__then:
        ; const {r0}, 1
        ld   r0, #%01
        jr   canMove__ret

if__25__then:
        ; const {r0}, 1
        ld   r0, #%01
        jr   canMove__ret

if__27__then:
        ; const {r0}, 1
        ld   r0, #%01
canMove__ret:
        ret

        ; u8* tileText@u8
        ; arg exponent (u8): r0
tileText_Pu8:
        ; move param.exponent{r2}, exponent{r0}
        ld   r2, r0
        ; branch param.exponent{r2} equals 0: if_29_then
        cp   r2, #%00
        jr   eq, if__29__then
        ; branch param.exponent{r2} notequals 1: if_30_else, if_30_then
        cp   r2, #%01
        jr   ne, if__30__else
        jr   if__30__then

if__29__then:
        ; 193:10 return string
        ; const t.1.1{r0}, [string-0]
        ld   r0, #hi(string_0)
        ld   r1, #lo(string_0)
        jr   tileText_Pu8__ret

if__30__else:
        ; branch param.exponent{r2} notequals 2: if_31_else, if_31_then
        cp   r2, #%02
        jr   ne, if__31__else
        jr   if__31__then

if__30__then:
        ; 196:10 return string
        ; const t.2.1{r2}, [string-1]
        ld   r2, #hi(string_1)
        ld   r3, #lo(string_1)
        ; move t.2.1{r0}, t.2.1{r2}
        ld   r0, r2
        ld   r1, r3
        jr   tileText_Pu8__ret

if__31__else:
        ; branch param.exponent{r2} notequals 3: if_32_else, if_32_then
        cp   r2, #%03
        jr   ne, if__32__else
        jr   if__32__then

if__31__then:
        ; 199:10 return string
        ; const t.3.1{r2}, [string-2]
        ld   r2, #hi(string_2)
        ld   r3, #lo(string_2)
        ; move t.3.1{r0}, t.3.1{r2}
        ld   r0, r2
        ld   r1, r3
        jr   tileText_Pu8__ret

if__32__else:
        ; branch param.exponent{r2} notequals 4: if_33_else, if_33_then
        cp   r2, #%04
        jr   ne, if__33__else
        jr   if__33__then

if__32__then:
        ; 202:10 return string
        ; const t.4.1{r2}, [string-3]
        ld   r2, #hi(string_3)
        ld   r3, #lo(string_3)
        ; move t.4.1{r0}, t.4.1{r2}
        ld   r0, r2
        ld   r1, r3
        jr   tileText_Pu8__ret

if__33__else:
        ; branch param.exponent{r2} notequals 5: if_34_else, if_34_then
        cp   r2, #%05
        jr   ne, if__34__else
        jr   if__34__then

if__33__then:
        ; 205:10 return string
        ; const t.5.1{r2}, [string-4]
        ld   r2, #hi(string_4)
        ld   r3, #lo(string_4)
        ; move t.5.1{r0}, t.5.1{r2}
        ld   r0, r2
        ld   r1, r3
        jr   tileText_Pu8__ret

if__34__else:
        ; branch param.exponent{r2} notequals 6: if_35_else, if_35_then
        cp   r2, #%06
        jr   ne, if__35__else
        jr   if__35__then

if__34__then:
        ; 208:10 return string
        ; const t.6.1{r2}, [string-5]
        ld   r2, #hi(string_5)
        ld   r3, #lo(string_5)
        ; move t.6.1{r0}, t.6.1{r2}
        ld   r0, r2
        ld   r1, r3
        jr   tileText_Pu8__ret

if__35__else:
        ; branch param.exponent{r2} notequals 7: if_36_else, if_36_then
        cp   r2, #%07
        jr   ne, if__36__else
        jr   if__36__then

if__35__then:
        ; 211:10 return string
        ; const t.7.1{r2}, [string-6]
        ld   r2, #hi(string_6)
        ld   r3, #lo(string_6)
        ; move t.7.1{r0}, t.7.1{r2}
        ld   r0, r2
        ld   r1, r3
        jr   tileText_Pu8__ret

if__36__else:
        ; branch param.exponent{r2} notequals 8: if_37_else, if_37_then
        cp   r2, #%08
        jr   ne, if__37__else
        jr   if__37__then

if__36__then:
        ; 214:10 return string
        ; const t.8.1{r2}, [string-7]
        ld   r2, #hi(string_7)
        ld   r3, #lo(string_7)
        ; move t.8.1{r0}, t.8.1{r2}
        ld   r0, r2
        ld   r1, r3
        jr   tileText_Pu8__ret

if__37__else:
        ; branch param.exponent{r2} notequals 9: if_38_else, if_38_then
        cp   r2, #%09
        jr   ne, if__38__else
        jr   if__38__then

if__37__then:
        ; 217:10 return string
        ; const t.9.1{r2}, [string-8]
        ld   r2, #hi(string_8)
        ld   r3, #lo(string_8)
        ; move t.9.1{r0}, t.9.1{r2}
        ld   r0, r2
        ld   r1, r3
        jr   tileText_Pu8__ret

if__38__else:
        ; branch param.exponent{r2} notequals 10: if_39_else, if_39_then
        cp   r2, #%0a
        jr   ne, if__39__else
        jr   if__39__then

if__38__then:
        ; 220:10 return string
        ; const t.10.1{r2}, [string-9]
        ld   r2, #hi(string_9)
        ld   r3, #lo(string_9)
        ; move t.10.1{r0}, t.10.1{r2}
        ld   r0, r2
        ld   r1, r3
        jr   tileText_Pu8__ret

if__39__else:
        ; branch param.exponent{r2} notequals 11: if_29_end, if_40_then
        cp   r2, #%0b
        jr   ne, if__29__end
        jr   if__40__then

if__39__then:
        ; 223:10 return string
        ; const t.11.1{r2}, [string-10]
        ld   r2, #hi(string_10)
        ld   r3, #lo(string_10)
        ; move t.11.1{r0}, t.11.1{r2}
        ld   r0, r2
        ld   r1, r3
        jr   tileText_Pu8__ret

if__29__end:
        ; 228:9 return string
        ; const t.13.1{r2}, [string-12]
        ld   r2, #hi(string_12)
        ld   r3, #lo(string_12)
        ; move t.13.1{r0}, t.13.1{r2}
        ld   r0, r2
        ld   r1, r3
        jr   tileText_Pu8__ret

if__40__then:
        ; 226:10 return string
        ; const t.12.1{r2}, [string-11]
        ld   r2, #hi(string_11)
        ld   r3, #lo(string_11)
        ; move t.12.1{r0}, t.12.1{r2}
        ld   r0, r2
        ld   r1, r3
tileText_Pu8__ret:
        ret

        ; void drawTileRow@i16@@u8
        ; arg offset (i16): r0
        ; arg text (u8*): r2
drawTileRow_Pi16_P_Pu8:
        ; save clobbered non-volatile registers
        push r8
        push r9
        ; const index.1{r4}, 0
        ld   r4, #%00
        ; 234:2 for index < 8
        jr   for__41

for__41__body:
        ; cast t.5.1{r5}(i16), index.2{r4}(u8)
        ld   r6, r4
        ld   r5, #0
        ; move t.4.1{r8}, param.text{r2}
        ld   r9, r3
        ld   r8, r2
        ; add t.4.2{r8}, t.5.1{r5}
        add  r9, r6
        adc  r8, r5
        ; load t.3.1{r5}, [t.4.2{r8}]
        lde  r5, @rr8
        ; addrof t.6.1{r6}, boardFrame
        ld   r6, #hi(var_3)
        ld   r7, #lo(var_3)
        ; add t.6.2{r6}, offset.1{r0}
        add  r7, r1
        adc  r6, r0
        ; store [t.6.2{r6}], t.3.1{r5}
        lde  @rr6, r5
        ; add offset.2{r0}, 1
        incw r0
        ; add index.3{r4}, 1
        inc  r4
for__41:
        ; branch index.2{r4} lt 8: for_41_body
        cp   r4, #%08
        jr   ult, for__41__body
        ; restore clobbered non-volatile registers
        pop  r9
        pop  r8
        ret

        ; void drawTileAt@i16@i16@u8
        ; arg row (i16): r0
        ; arg column (i16): r2
        ; arg exponent (u8): r4
drawTileAt_Pi16_Pi16_Pu8:
        ; save clobbered non-volatile registers
        push r8
        push r9
        push r10
        push r11
        push r12
        push r13
        ; move param.exponent{r8}, exponent{r4}
        ld   r8, r4
        ; branch param.exponent{r8} equals 0: drawTileAt@i16@i16@u8_ret
        cp   r8, #%00
        jr   eq, drawTileAt_Pi16_Pi16_Pu8__ret
        ; move t.8.1{r9}, param.row{r0}
        ld   r10, r1
        ld   r9, r0
        ; sub t.8.1{r9}, 3
        sub  r10, #%03
        sbc  r9, #%00
        ; mul t.7.1{r9}, 35
        ld   %12, r9
        ld   %13, r10
        ld   %14, #%00
        ld   %15, #%23
        srp  #%10
        call %00BA ; mul
        srp  #%20
        ld   r9, %12
        ld   r10, %13
        ; add t.6.1{r9}, param.column{r2}
        add  r10, r3
        adc  r9, r2
        ; sub offset.1{r9}, 1
        sub  r10, #%01
        sbc  r9, #%00
        ; const t.9.1{r2}, [string-13]
        ld   r2, #hi(string_13)
        ld   r3, #lo(string_13)
        ; move offset.1{r0}, offset.1{r9}
        ld   r0, r9
        ld   r1, r10
        ; call drawTileRow@i16@@u8[offset.1{r0}, t.9.1{r2}]
        call drawTileRow_Pi16_P_Pu8
        ; add offset.2{r9}, 35
        add  r10, #%23
        adc  r9, #%00
        ; const t.10.1{r11}, 124
        ld   r11, #%7c
        ; addrof t.11.1{r12}, boardFrame
        ld   r12, #hi(var_3)
        ld   r13, #lo(var_3)
        ; add t.11.2{r12}, offset.2{r9}
        add  r13, r10
        adc  r12, r9
        ; store [t.11.2{r12}], t.10.1{r11}
        lde  @rr12, r11
        ; move t.15.1{r12}, offset.2{r9}
        ld   r13, r10
        ld   r12, r9
        ; add t.15.1{r12}, 8
        add  r13, #%08
        adc  r12, #%00
        ; sub t.14.1{r12}, 1
        decw r12
        ; addrof t.13.1{r2}, boardFrame
        ld   r2, #hi(var_3)
        ld   r3, #lo(var_3)
        ; add t.13.2{r2}, t.14.1{r12}
        add  r3, r13
        adc  r2, r12
        ; store [t.13.2{r2}], t.12.1{r11}
        lde  @rr2, r11
        ; move param.exponent{r0}, param.exponent{r8}
        ld   r0, r8
        ; call text.1{r0} = tileText@u8[param.exponent{r0}] -> u8*
        call tileText_Pu8
        ; const index.1{r8}, 0
        ld   r8, #%00
        ; 250:2 for index < 6
        jr   for__43

for__43__body:
        ; cast t.18.1{r11}(i16), index.2{r8}(u8)
        ld   r12, r8
        ld   r11, #0
        ; move t.17.1{r4}, text.1{r0}
        ld   r5, r1
        ld   r4, r0
        ; add t.17.2{r4}, t.18.1{r11}
        add  r5, r12
        adc  r4, r11
        ; load t.16.1{r11}, [t.17.2{r4}]
        lde  r11, @rr4
        ; cast t.22.1{r12}(i16), index.2{r8}(u8)
        ld   r13, r8
        ld   r12, #0
        ; move t.21.1{r4}, offset.2{r9}
        ld   r4, r9
        ld   r5, r10
        ; add t.21.1{r4}, t.22.1{r12}
        add  r5, r13
        adc  r4, r12
        ; move t.20.1{r12}, t.21.1{r4}
        ld   r13, r5
        ld   r12, r4
        ; add t.20.1{r12}, 1
        incw r12
        ; addrof t.19.1{r4}, boardFrame
        ld   r4, #hi(var_3)
        ld   r5, #lo(var_3)
        ; add t.19.2{r4}, t.20.1{r12}
        add  r5, r13
        adc  r4, r12
        ; store [t.19.2{r4}], t.16.1{r11}
        lde  @rr4, r11
        ; add index.3{r8}, 1
        inc  r8
for__43:
        ; branch index.2{r8} lt 6: for_43_body
        cp   r8, #%06
        jr   ult, for__43__body
        ; move t.23.1{r0}, offset.2{r9}
        ld   r0, r9
        ld   r1, r10
        ; add t.23.1{r0}, 35
        add  r1, #%23
        adc  r0, #%00
        ; const t.24.1{r2}, [string-13]
        ld   r2, #hi(string_13)
        ld   r3, #lo(string_13)
        ; call drawTileRow@i16@@u8[t.23.1{r0}, t.24.1{r2}]
        call drawTileRow_Pi16_P_Pu8
drawTileAt_Pi16_Pi16_Pu8__ret:
        ; restore clobbered non-volatile registers
        pop  r13
        pop  r12
        pop  r11
        pop  r10
        pop  r9
        pop  r8
        ret

        ; void drawFrame
drawFrame:
        ; const arg.0.0{r0}, 12
        ld   r0, #%0c
        ; call printChar@u8[arg.0.0{r0}]
        call printChar_Pu8
        ; const arg.1.0{r0}, 1
        ld   r0, #%00
        ld   r1, #%01
        ; const arg.1.1{r2}, 0
        ld   r2, #%00
        ld   r3, #%00
        ; call setCursor@i16@i16[arg.1.0{r0}, arg.1.1{r2}]
        call setCursor_Pi16_Pi16
        ; const t.0.1{r0}, [string-14]
        ld   r0, #hi(string_14)
        ld   r1, #lo(string_14)
        ; call printString@@u8[t.0.1{r0}]
        call printString_P_Pu8
        ret

        ; void clearBoard
clearBoard:
        ; const offset.1{r0}, 0
        ld   r0, #%00
        ld   r1, #%00
        ; 265:2 for offset < 525
        jr   for__44

for__44__body:
        ; const t.1.1{r2}, 32
        ld   r2, #%20
        ; addrof t.2.1{r4}, boardFrame
        ld   r4, #hi(var_3)
        ld   r5, #lo(var_3)
        ; add t.2.2{r4}, offset.2{r0}
        add  r5, r1
        adc  r4, r0
        ; store [t.2.2{r4}], t.1.1{r2}
        lde  @rr4, r2
        ; add offset.3{r0}, 1
        incw r0
for__44:
        ; branch offset.2{r0} lt 525: for_44_body
        cp   r0, #%02
        jr   lt, for__44__body
        jr   ne, .lt2
        cp   r1, #%0d
        jr   ult, for__44__body
.lt2:
        ret

        ; void presentBoard
        ; var column.2 (i16): SP+8
presentBoard:
        decw SPH
        decw SPH
        ; save clobbered non-volatile registers
        push r8
        push r9
        push r10
        push r11
        push r12
        push r13
        push r14
        push r15
        ; const offset.1{r8}, 0
        ld   r8, #%00
        ld   r9, #%00
        ; const row.1{r10}, 0
        ld   r10, #%00
        ld   r11, #%00
        ; 275:2 for row < 15
        jr   for__45

for__45__body:
        ; const printing.1{r12}, 0
        ld   r12, #%00
        ; const column.1{r4}, 0
        ld   r4, #%00
        ld   r5, #%00
        ; 277:3 for column < 35
        jr   for__46

for__46__body:
        ; addrof t.5.1{r6}, boardFrame
        ld   r6, #hi(var_3)
        ld   r7, #lo(var_3)
        ; add t.5.2{r6}, offset.3{r8}
        add  r7, r9
        adc  r6, r8
        ; load character.1{r13}, [t.5.2{r6}]
        lde  r13, @rr6
        ; 279:4 if character != [...]
        ; addrof t.7.1{r6}, displayedFrame
        ld   r6, #hi(var_4)
        ld   r7, #lo(var_4)
        ; add t.7.2{r6}, offset.3{r8}
        add  r7, r9
        adc  r6, r8
        ; load t.6.1{r2}, [t.7.2{r6}]
        lde  r2, @rr6
        ; branch character.1{r13} notequals t.6.1{r2}: if_47_then
        cp   r13, r2
        jr   ne, if__47__then
        ; const printing.3{r12}, 0
        ld   r12, #%00
        ; addrof memVarAddr{r14}, column.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; store [memVarAddr{r14}], column.2{r4}
        lde  @rr14, r4
        incw r14
        lde  @rr14, r5
        decw r14
        jr   if__47__end

if__47__then:
        ; branch printing.2{r12} equals 0: if_48_then
        cp   r12, #%00
        jr   eq, if__48__then
        ; addrof memVarAddr{r14}, column.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; store [memVarAddr{r14}], column.2{r4}
        lde  @rr14, r4
        incw r14
        lde  @rr14, r5
        decw r14
        jr   if__48__end

if__48__then:
        ; move t.8.1{r0}, row.2{r10}
        ld   r0, r10
        ld   r1, r11
        ; add t.8.1{r0}, 3
        add  r1, #%03
        adc  r0, #%00
        ; move t.9.1{r2}, column.2{r4}
        ld   r2, r4
        ld   r3, r5
        ; addrof memVarAddr{r14}, column.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; store [memVarAddr{r14}], column.2{r4}
        lde  @rr14, r4
        incw r14
        lde  @rr14, r5
        decw r14
        ; add t.9.1{r2}, 1
        incw r2
        ; call setCursor@i16@i16[t.8.1{r0}, t.9.1{r2}]
        call setCursor_Pi16_Pi16
        ; const printing.6{r12}, 1
        ld   r12, #%01
if__48__end:
        ; move character.1{r0}, character.1{r13}
        ld   r0, r13
        ; call printChar@u8[character.1{r0}]
        call printChar_Pu8
        ; addrof t.10.1{r4}, displayedFrame
        ld   r4, #hi(var_4)
        ld   r5, #lo(var_4)
        ; add t.10.2{r4}, offset.3{r8}
        add  r5, r9
        adc  r4, r8
        ; store [t.10.2{r4}], character.1{r13}
        lde  @rr4, r13
if__47__end:
        ; add offset.5{r8}, 1
        incw r8
        ; addrof memVarAddr{r14}, column.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; load column.2{r4}, [memVarAddr{r14}]
        lde  r4, @rr14
        incw r14
        lde  r5, @rr14
        decw r14
        ; add column.5{r4}, 1
        incw r4
for__46:
        ; branch column.2{r4} lt 35: for_46_body
        cp   r4, #%00
        jr   lt, for__46__body
        jr   ne, .lt3
        cp   r5, #%23
        jr   ult, for__46__body
.lt3:
        ; add row.4{r10}, 1
        incw r10
for__45:
        ; branch row.2{r10} lt 15: for_45_body
        cp   r10, #%00
        jr   lt, for__45__body
        jr   ne, .lt4
        cp   r11, #%0f
        jr   ult, for__45__body
.lt4:
        ; const arg.2.0{r0}, 21
        ld   r0, #%00
        ld   r1, #%15
        ; const arg.2.1{r2}, 0
        ld   r2, #%00
        ld   r3, #%00
        ; call setCursor@i16@i16[arg.2.0{r0}, arg.2.1{r2}]
        call setCursor_Pi16_Pi16
        ; restore clobbered non-volatile registers
        pop  r15
        pop  r14
        pop  r13
        pop  r12
        pop  r11
        pop  r10
        pop  r9
        pop  r8
        incw SPH
        incw SPH
        ret

        ; void drawBoard
        ; var boardColumn.2 (u8): SP+8
drawBoard:
        decw SPH
        ; save clobbered non-volatile registers
        push r8
        push r9
        push r10
        push r11
        push r12
        push r13
        push r14
        push r15
        ; call clearBoard[]
        call clearBoard
        ; const index.1{r8}, 0
        ld   r8, #%00
        ; const row.1{r9}, 3
        ld   r9, #%00
        ld   r10, #%03
        ; const boardRow.1{r11}, 0
        ld   r11, #%00
        ; 303:2 for boardRow < 4
        jr   for__49

for__49__body:
        ; const column.1{r12}, 1
        ld   r12, #%00
        ld   r13, #%01
        ; const boardColumn.1{r5}, 0
        ld   r5, #%00
        ; 305:3 for boardColumn < 4
        ; addrof memVarAddr{r14}, boardColumn.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; store [memVarAddr{r14}], boardColumn.2{r5}
        lde  @rr14, r5
        ; move boardColumn.2{r0}, boardColumn.2{r5}
        ld   r0, r5
        jr   for__50

for__50__body:
        ; addrof memVarAddr{r14}, boardColumn.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; store [memVarAddr{r14}], boardColumn.2{r0}
        lde  @rr14, r0
        ; cast t.7.1{r5}(i16), index.3{r8}(u8)
        ld   r6, r8
        ld   r5, #0
        ; addrof t.6.1{r2}, tiles
        ld   r2, #hi(var_0)
        ld   r3, #lo(var_0)
        ; add t.6.2{r2}, t.7.1{r5}
        add  r3, r6
        adc  r2, r5
        ; load t.5.1{r4}, [t.6.2{r2}]
        lde  r4, @rr2
        ; move row.2{r0}, row.2{r9}
        ld   r0, r9
        ld   r1, r10
        ; move column.2{r2}, column.2{r12}
        ld   r2, r12
        ld   r3, r13
        ; call drawTileAt@i16@i16@u8[row.2{r0}, column.2{r2}, t.5.1{r4}]
        call drawTileAt_Pi16_Pi16_Pu8
        ; add index.4{r8}, 1
        inc  r8
        ; add column.3{r12}, 9
        add  r13, #%09
        adc  r12, #%00
        ; load boardColumn.2{r0}, [memVarAddr{r14}]
        lde  r0, @rr14
        ; add boardColumn.3{r0}, 1
        inc  r0
for__50:
        ; branch boardColumn.2{r0} lt 4: for_50_body
        cp   r0, #%04
        jr   ult, for__50__body
        ; add row.4{r9}, 4
        add  r10, #%04
        adc  r9, #%00
        ; add boardRow.4{r11}, 1
        inc  r11
for__49:
        ; branch boardRow.2{r11} lt 4: for_49_body
        cp   r11, #%04
        jr   ult, for__49__body
        ; call presentBoard[]
        call presentBoard
        ; restore clobbered non-volatile registers
        pop  r15
        pop  r14
        pop  r13
        pop  r12
        pop  r11
        pop  r10
        pop  r9
        pop  r8
        incw SPH
        ret

        ; void animateSlide
        ; var source.2 (u8): SP+8
animateSlide:
        decw SPH
        ; save clobbered non-volatile registers
        push r8
        push r9
        push r10
        push r11
        push r12
        push r13
        push r14
        push r15
        ; const frame.1{r8}, 1
        ld   r8, #%00
        ld   r9, #%01
        ; 345:2 for frame <= 4
        jr   for__51

for__51__body:
        ; const remaining.1{r10}, 4
        ld   r10, #%00
        ld   r11, #%04
        ; sub remaining.2{r10}, frame.2{r8}
        sub  r11, r9
        sbc  r10, r8
        ; call clearBoard[]
        call clearBoard
        ; const source.1{r12}, 0
        ld   r12, #%00
        ; 351:3 for source < 16
        jr   for__52

for__52__body:
        ; cast t.12.1{r5}(i16), source.2{r12}(u8)
        ld   r6, r12
        ld   r5, #0
        ; addrof t.11.1{r2}, moveTiles
        ld   r2, #hi(var_1)
        ld   r3, #lo(var_1)
        ; add t.11.2{r2}, t.12.1{r5}
        add  r3, r6
        adc  r2, r5
        ; load exponent.1{r4}, [t.11.2{r2}]
        lde  r4, @rr2
        ; 353:4 if exponent == 0
        ; branch exponent.1{r4} notequals 0: if_53_end
        cp   r4, #%00
        jr   ne, if__53__end
        ; addrof memVarAddr{r14}, source.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; store [memVarAddr{r14}], source.2{r12}
        lde  @rr14, r12
        jr   for__52__continue

if__53__end:
        ; cast t.14.1{r5}(i16), source.2{r12}(u8)
        ld   r6, r12
        ld   r5, #0
        ; addrof t.13.1{r2}, moveTargets
        ld   r2, #hi(var_2)
        ld   r3, #lo(var_2)
        ; add t.13.2{r2}, t.14.1{r5}
        add  r3, r6
        adc  r2, r5
        ; load target.1{r13}, [t.13.2{r2}]
        lde  r13, @rr2
        ; move t.16.1{r5}, source.2{r12}
        ld   r5, r12
        ; shiftright t.16.1{r5}, 2
        rcf
        rrc  r5
        rcf
        rrc  r5
        ; cast t.15.1{r5}(i16), t.16.1{r5}(u8)
        ld   r6, r5
        ld   r5, #0
        ; shiftleft sourceRow.1{r5}, 2
        add r6, r6
        adc r5, r5
        add r6, r6
        adc r5, r5
        ; move t.18.1{r7}, source.2{r12}
        ld   r7, r12
        ; and t.18.1{r7}, 3
        and  r7, #%03
        ; cast t.17.1{r2}(i16), t.18.1{r7}(u8)
        ld   r3, r7
        ld   r2, #0
        ; mul sourceColumn.1{r2}, 9
        ld   %12, r2
        ld   %13, r3
        ld   %14, #%00
        ld   %15, #%09
        srp  #%10
        call %00BA ; mul
        srp  #%20
        ld   r2, %12
        ld   r3, %13
        ; move t.20.1{r7}, target.1{r13}
        ld   r7, r13
        ; shiftright t.20.1{r7}, 2
        rcf
        rrc  r7
        rcf
        rrc  r7
        ; cast t.19.1{r0}(i16), t.20.1{r7}(u8)
        ld   r1, r7
        ld   r0, #0
        ; shiftleft targetRow.1{r0}, 2
        add r1, r1
        adc r0, r0
        add r1, r1
        adc r0, r0
        ; and t.22.1{r13}, 3
        and  r13, #%03
        ; addrof memVarAddr{r14}, source.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; store [memVarAddr{r14}], source.2{r12}
        lde  @rr14, r12
        ; cast t.21.1{r12}(i16), t.22.1{r13}(u8)
        ld   r12, #0
        ; mul targetColumn.1{r12}, 9
        ld   %12, r12
        ld   %13, r13
        ld   %14, #%00
        ld   %15, #%09
        srp  #%10
        call %00BA ; mul
        srp  #%20
        ld   r12, %12
        ld   r13, %13
        ; mul t.24.1{r5}, remaining.2{r10}
        ld   %12, r5
        ld   %13, r6
        ld   %14, r10
        ld   %15, r11
        srp  #%10
        call %00BA ; mul
        srp  #%20
        ld   r5, %12
        ld   r6, %13
        ; mul t.25.1{r0}, frame.2{r8}
        ld   %12, r0
        ld   %13, r1
        ld   %14, r8
        ld   %15, r9
        srp  #%10
        call %00BA ; mul
        srp  #%20
        ld   r0, %12
        ld   r1, %13
        ; add t.23.1{r5}, t.25.1{r0}
        add  r6, r1
        adc  r5, r0
        ; shiftright row.1{r5}, 2
        sra  r5
        rrc  r6
        sra  r5
        rrc  r6
        ; mul t.27.1{r2}, remaining.2{r10}
        ld   %12, r2
        ld   %13, r3
        ld   %14, r10
        ld   %15, r11
        srp  #%10
        call %00BA ; mul
        srp  #%20
        ld   r2, %12
        ld   r3, %13
        ; mul t.28.1{r12}, frame.2{r8}
        ld   %12, r12
        ld   %13, r13
        ld   %14, r8
        ld   %15, r9
        srp  #%10
        call %00BA ; mul
        srp  #%20
        ld   r12, %12
        ld   r13, %13
        ; add t.26.1{r2}, t.28.1{r12}
        add  r3, r13
        adc  r2, r12
        ; move column.1{r12}, t.26.1{r2}
        ld   r13, r3
        ld   r12, r2
        ; shiftright column.1{r12}, 2
        sra  r12
        rrc  r13
        sra  r12
        rrc  r13
        ; move t.29.1{r0}, row.1{r5}
        ld   r0, r5
        ld   r1, r6
        ; add t.29.1{r0}, 3
        add  r1, #%03
        adc  r0, #%00
        ; move t.30.1{r2}, column.1{r12}
        ld   r2, r12
        ld   r3, r13
        ; add t.30.1{r2}, 1
        incw r2
        ; call drawTileAt@i16@i16@u8[t.29.1{r0}, t.30.1{r2}, exponent.1{r4}]
        call drawTileAt_Pi16_Pi16_Pu8
for__52__continue:
        ; addrof memVarAddr{r14}, source.2
        ld   r14, SPH
        ld   r15, SPL
        add  r15, #%08
        adc  r14, #%00
        ; load source.2{r12}, [memVarAddr{r14}]
        lde  r12, @rr14
        ; add source.4{r12}, 1
        inc  r12
for__52:
        ; branch source.2{r12} lt 16: for_52_body
        cp   r12, #%10
        jr   ult, for__52__body
        ; call presentBoard[]
        call presentBoard
        ; call waitFrame[]
        call waitFrame
        ; move frame.4{r0}, frame.2{r8}
        ld   r0, r8
        ld   r1, r9
        ; add frame.4{r0}, 1
        incw r0
        ; move frame.2{r8}, frame.4{r0}
        ld   r9, r1
        ld   r8, r0
for__51:
        ; branch frame.2{r8} lteq 4: for_51_body
        cp   r8, #%00
        jr   lt, for__51__body
        jr   ne, .lt5
        cp   r9, #%04
        jr   ule, for__51__body
.lt5:
        ; restore clobbered non-volatile registers
        pop  r15
        pop  r14
        pop  r13
        pop  r12
        pop  r11
        pop  r10
        pop  r9
        pop  r8
        incw SPH
        ret

        ; void drawScore
drawScore:
        ; save clobbered non-volatile registers
        push r8
        push r9
        ; const arg.0.0{r0}, 0
        ld   r0, #%00
        ld   r1, #%00
        ; const arg.0.1{r2}, 0
        ld   r2, #%00
        ld   r3, #%00
        ; call setCursor@i16@i16[arg.0.0{r0}, arg.0.1{r2}]
        call setCursor_Pi16_Pi16
        ; const t.0.1{r0}, [string-15]
        ld   r0, #hi(string_15)
        ld   r1, #lo(string_15)
        ; call printString@@u8[t.0.1{r0}]
        call printString_P_Pu8
        ; addrof a.1.1{r8}, score
        ld   r8, #hi(var_5)
        ld   r9, #lo(var_5)
        ; load t.2.1{r0}, [a.1.1{r8}]
        lde  r0, @rr8
        incw r8
        lde  r1, @rr8
        incw r8
        lde  r2, @rr8
        incw r8
        lde  r3, @rr8
        sub  r9, #3
        sbc  r8, #0
        ; call printUint@i32[t.2.1{r0}]
        call printUint_Pi32
        ; const t.3.1{r0}, [string-16]
        ld   r0, #hi(string_16)
        ld   r1, #lo(string_16)
        ; call printString@@u8[t.3.1{r0}]
        call printString_P_Pu8
        ; restore clobbered non-volatile registers
        pop  r9
        pop  r8
        ret

        ; void drawStatus
drawStatus:
        ; save clobbered non-volatile registers
        push r8
        push r9
        push r10
        ; const arg.0.0{r0}, 19
        ld   r0, #%00
        ld   r1, #%13
        ; const arg.0.1{r2}, 0
        ld   r2, #%00
        ld   r3, #%00
        ; call setCursor@i16@i16[arg.0.0{r0}, arg.0.1{r2}]
        call setCursor_Pi16_Pi16
        ; const t.0.1{r0}, [string-17]
        ld   r0, #hi(string_17)
        ld   r1, #lo(string_17)
        ; call printString@@u8[t.0.1{r0}]
        call printString_P_Pu8
        ; const arg.2.0{r0}, 19
        ld   r0, #%00
        ld   r1, #%13
        ; const arg.2.1{r2}, 0
        ld   r2, #%00
        ld   r3, #%00
        ; call setCursor@i16@i16[arg.2.0{r0}, arg.2.1{r2}]
        call setCursor_Pi16_Pi16
        ; 386:2 if gameState == 1
        ; addrof a.1.1{r8}, gameState
        ld   r8, #hi(var_6)
        ld   r9, #lo(var_6)
        ; load t.2.1{r10}, [a.1.1{r8}]
        lde  r10, @rr8
        ; branch t.2.1{r10} equals 1: if_54_then
        cp   r10, #%01
        jr   eq, if__54__then
        ; 389:7 if gameState == 2
        ; branch t.5.1{r10} notequals 2: drawStatus_ret, if_55_then
        cp   r10, #%02
        jr   ne, drawStatus__ret
        jr   if__55__then

if__54__then:
        ; const t.3.1{r0}, [string-18]
        ld   r0, #hi(string_18)
        ld   r1, #lo(string_18)
        ; call printString@@u8[t.3.1{r0}]
        call printString_P_Pu8
        jr   drawStatus__ret

if__55__then:
        ; const t.6.1{r0}, [string-19]
        ld   r0, #hi(string_19)
        ld   r1, #lo(string_19)
        ; call printString@@u8[t.6.1{r0}]
        call printString_P_Pu8
drawStatus__ret:
        ; restore clobbered non-volatile registers
        pop  r10
        pop  r9
        pop  r8
        ret

        ; void newGame
newGame:
        ; save clobbered non-volatile registers
        push r8
        push r9
        push r10
        push r11
        push r12
        push r13
        ; const index.1{r8}, 0
        ld   r8, #%00
        ; 395:2 for index < 16
        jr   for__56

for__56__body:
        ; const t.1.1{r9}, 0
        ld   r9, #%00
        ; cast t.3.1{r10}(i16), index.2{r8}(u8)
        ld   r11, r8
        ld   r10, #0
        ; addrof t.2.1{r12}, tiles
        ld   r12, #hi(var_0)
        ld   r13, #lo(var_0)
        ; add t.2.2{r12}, t.3.1{r10}
        add  r13, r11
        adc  r12, r10
        ; store [t.2.2{r12}], t.1.1{r9}
        lde  @rr12, r9
        ; add index.3{r8}, 1
        inc  r8
for__56:
        ; branch index.2{r8} lt 16: for_56_body
        cp   r8, #%10
        jr   ult, for__56__body
        ; const t.4.1{r8}, 0
        ld   r8, #%00
        ld   r9, #%00
        ld   r10, #%00
        ld   r11, #%00
        ; addrof a.5.1{r12}, score
        ld   r12, #hi(var_5)
        ld   r13, #lo(var_5)
        ; store [a.5.1{r12}], t.4.1{r8}
        lde  @rr12, r8
        incw r12
        lde  @rr12, r9
        incw r12
        lde  @rr12, r10
        incw r12
        lde  @rr12, r11
        sub  r13, #3
        sbc  r12, #0
        ; const t.6.1{r8}, 0
        ld   r8, #%00
        ; addrof a.7.1{r10}, gameState
        ld   r10, #hi(var_6)
        ld   r11, #lo(var_6)
        ; store [a.7.1{r10}], t.6.1{r8}
        lde  @rr10, r8
        ; call spawnTile[]
        call spawnTile
        ; call spawnTile[]
        call spawnTile
        ; call drawBoard[]
        call drawBoard
        ; call drawScore[]
        call drawScore
        ; call drawStatus[]
        call drawStatus
        ; const arg.5.0{r0}, 21
        ld   r0, #%00
        ld   r1, #%15
        ; const arg.5.1{r2}, 0
        ld   r2, #%00
        ld   r3, #%00
        ; call setCursor@i16@i16[arg.5.0{r0}, arg.5.1{r2}]
        call setCursor_Pi16_Pi16
        ; restore clobbered non-volatile registers
        pop  r13
        pop  r12
        pop  r11
        pop  r10
        pop  r9
        pop  r8
        ret

        ; void main
main:
        ; save clobbered non-volatile registers
        push r8
        push r9
        push r10
        push r11
        push r12
        push r13
        ; begin initialize global variables
        ; const t.2.1{r8}, 0
        ld   r8, #%00
        ld   r9, #%00
        ld   r10, #%00
        ld   r11, #%00
        ; addrof a.3.1{r12}, score
        ld   r12, #hi(var_5)
        ld   r13, #lo(var_5)
        ; store [a.3.1{r12}], t.2.1{r8}
        lde  @rr12, r8
        incw r12
        lde  @rr12, r9
        incw r12
        lde  @rr12, r10
        incw r12
        lde  @rr12, r11
        sub  r13, #3
        sbc  r12, #0
        ; const t.4.1{r8}, 0
        ld   r8, #%00
        ; addrof a.5.1{r10}, gameState
        ld   r10, #hi(var_6)
        ld   r11, #lo(var_6)
        ; store [a.5.1{r10}], t.4.1{r8}
        lde  @rr10, r8
        ; end initialize global variables
        ; const arg.0.0{r0}, 7439742
        ld   r0, #%00
        ld   r1, #%71
        ld   r2, #%85
        ld   r3, #%7e
        ; call initRandom@i32[arg.0.0{r0}]
        call initRandom_Pi32
        ; call drawFrame[]
        call drawFrame
        ; call newGame[]
        call newGame
        ; 412:2 while true
        jr   while__57

if__58__end:
        ; branch key.1{r8} equals 114: if_59_then
        cp   r9, #%72
        jr   ne, .notEquals6
        cp   r8, #%00
        jr   eq, if__59__then
.notEquals6:
        ; branch key.1{r8} equals 82: if_59_then
        cp   r9, #%52
        jr   ne, .notEquals7
        cp   r8, #%00
        jr   eq, if__59__then
.notEquals7:
        ; 421:3 if gameState != 0
        ; addrof a.6.1{r10}, gameState
        ld   r10, #hi(var_6)
        ld   r11, #lo(var_6)
        ; load t.7.1{r12}, [a.6.1{r10}]
        lde  r12, @rr10
        ; branch t.7.1{r12} notequals 0: while_57
        cp   r12, #%00
        jr   ne, while__57
        ; branch key.1{r8} equals 1: if_62_then, or_64
        cp   r9, #%01
        jr   ne, .notEquals8
        cp   r8, #%00
        jr   eq, if__62__then
.notEquals8:
        jr   or__64

if__59__then:
        ; call newGame[]
        call newGame
        jr   while__57

or__64:
        ; branch key.1{r8} equals 97: if_62_then
        cp   r9, #%61
        jr   ne, .notEquals9
        cp   r8, #%00
        jr   eq, if__62__then
.notEquals9:
        ; branch key.1{r8} equals 65: if_62_then
        cp   r9, #%41
        jr   ne, .notEquals10
        cp   r8, #%00
        jr   eq, if__62__then
.notEquals10:
        ; branch key.1{r8} equals 2: if_65_then
        cp   r9, #%02
        jr   ne, .notEquals11
        cp   r8, #%00
        jr   eq, if__65__then
.notEquals11:
        ; branch key.1{r8} equals 100: if_65_then, or_66
        cp   r9, #%64
        jr   ne, .notEquals12
        cp   r8, #%00
        jr   eq, if__65__then
.notEquals12:
        jr   or__66

if__62__then:
        ; const direction.2{r8}, 0
        ld   r8, #%00
        ; move direction.3{r0}, direction.2{r8}
        ld   r0, r8
        jr   if__62__end

or__66:
        ; branch key.1{r8} equals 68: if_65_then
        cp   r9, #%44
        jr   ne, .notEquals13
        cp   r8, #%00
        jr   eq, if__65__then
.notEquals13:
        ; branch key.1{r8} equals 3: if_68_then
        cp   r9, #%03
        jr   ne, .notEquals14
        cp   r8, #%00
        jr   eq, if__68__then
.notEquals14:
        ; branch key.1{r8} equals 119: if_68_then, or_69
        cp   r9, #%77
        jr   ne, .notEquals15
        cp   r8, #%00
        jr   eq, if__68__then
.notEquals15:
        jr   or__69

if__65__then:
        ; const direction.4{r8}, 1
        ld   r8, #%01
        ; move direction.3{r0}, direction.4{r8}
        ld   r0, r8
        jr   if__62__end

or__69:
        ; branch key.1{r8} equals 87: if_68_then
        cp   r9, #%57
        jr   ne, .notEquals16
        cp   r8, #%00
        jr   eq, if__68__then
.notEquals16:
        ; branch key.1{r8} equals 4: if_71_then
        cp   r9, #%04
        jr   ne, .notEquals17
        cp   r8, #%00
        jr   eq, if__71__then
.notEquals17:
        ; branch key.1{r8} equals 115: if_71_then, or_72
        cp   r9, #%73
        jr   ne, .notEquals18
        cp   r8, #%00
        jr   eq, if__71__then
.notEquals18:
        jr   or__72

if__68__then:
        ; const direction.5{r8}, 2
        ld   r8, #%02
        ; move direction.3{r0}, direction.5{r8}
        ld   r0, r8
        jr   if__62__end

or__72:
        ; branch key.1{r8} notequals 83: while_57, if_71_then
        cp   r9, #%53
        jr   ne, while__57
        cp   r8, #%00
        jr   ne, while__57
if__71__then:
        ; const direction.6{r8}, 3
        ld   r8, #%03
        ; move direction.3{r0}, direction.6{r8}
        ld   r0, r8
if__62__end:
        ; 441:3 if !slide@u8([ExprVarAccess[varName=direction, index=1, scope=function, type=u8, varIsArray=false, location=441:13]])
        ; call t.8.1{r0} = slide@u8[direction.3{r0}] -> bool
        call slide_Pu8
        ; branch t.8.1{r0} equals 0: while_57
        cp   r0, #%00
        jr   eq, while__57
        ; call animateSlide[]
        call animateSlide
        ; call spawnTile[]
        call spawnTile
        ; 447:3 if gameState == 0 && !canMove([])
        ; addrof a.9.1{r8}, gameState
        ld   r8, #hi(var_6)
        ld   r9, #lo(var_6)
        ; load t.10.1{r10}, [a.9.1{r8}]
        lde  r10, @rr8
        ; branch t.10.1{r10} notequals 0: if_75_end
        cp   r10, #%00
        jr   ne, if__75__end
        ; call t.11.1{r0} = canMove[] -> bool
        call canMove
        ; branch t.11.1{r0} notequals 0: if_75_end
        cp   r0, #%00
        jr   ne, if__75__end
        ; const t.12.1{r8}, 2
        ld   r8, #%02
        ; addrof a.13.1{r10}, gameState
        ld   r10, #hi(var_6)
        ld   r11, #lo(var_6)
        ; store [a.13.1{r10}], t.12.1{r8}
        lde  @rr10, r8
if__75__end:
        ; call drawBoard[]
        call drawBoard
        ; call drawScore[]
        call drawScore
        ; 452:3 if gameState != 0
        ; addrof a.14.1{r8}, gameState
        ld   r8, #hi(var_6)
        ld   r9, #lo(var_6)
        ; load t.15.1{r10}, [a.14.1{r8}]
        lde  r10, @rr8
        ; branch t.15.1{r10} equals 0: if_77_end
        cp   r10, #%00
        jr   eq, if__77__end
        ; call drawStatus[]
        call drawStatus
if__77__end:
        ; const arg.11.0{r0}, 21
        ld   r0, #%00
        ld   r1, #%15
        ; const arg.11.1{r2}, 0
        ld   r2, #%00
        ld   r3, #%00
        ; call setCursor@i16@i16[arg.11.0{r0}, arg.11.1{r2}]
        call setCursor_Pi16_Pi16
while__57:
        ; call key.1{r0} = getChar[] -> i16
        call getChar
        ; move key.1{r8}, key.1{r0}
        ld   r9, r1
        ld   r8, r0
        ; 414:3 if key == 27
        ; branch key.1{r8} notequals 27: if_58_end
        cp   r9, #%1b
        jr   ne, if__58__end
        cp   r8, #%00
        jr   ne, if__58__end
        ; restore clobbered non-volatile registers
        pop  r13
        pop  r12
        pop  r11
        pop  r10
        pop  r9
        pop  r8
        ret

        ; void printString@@u8
printString_P_Pu8:
        ld   r2, r0
        ld   r3, r1
        jr   .loop
.print:
        call printChar_Pu8
        incw r2
.loop:
        lde  r0, @rr2
        or   r0, r0
        jr   nz, .print
        ret

        ; void printChar@u8
printChar_Pu8:
        cp    r0, #%0a
        jr    ne, .1
        ld    r0, #%0d
.1:
        ld    %15, r0
        jp    %0818

        ; void printUint@i32
printUint_Pi32:
        ld   r4, #1
        ld   r5, #%28
        ld   r6, #8
.push:
        push @r5
        inc  r5
        djnz r6, .push
        ; result
        clr     r11
        clr     r12
        clr     r13
        clr     r14
        clr     r15
        ; summand (bcd-shifted power of 2)
        clr     r6
        clr     r7
        clr     r8
        clr     r9
        ld      r10, #1
        ; counter
        ld      r5, #%20
.1:
        sra     r0
        rrc     r1
        rrc     r2
        rrc     r3
        jr      nc, .2
        add     r15, r10
        da      r15
        adc     r14, r9
        da      r14
        adc     r13, r8
        da      r13
        adc     r12, r7
        da      r12
        adc     r11, r6
        da      r11
.2:
        add     r10, r10
        da      r10
        adc     r9, r9
        da      r9
        adc     r8, r8
        da      r8
        adc     r7, r7
        da      r7
        adc     r6, r6
        da      r6
        djnz    r5, .1
        ld      r6, #%2b
        ; counter
        ld      r7, #10
.loop:
        ld      r5, @r6
        tm      r7, #1
        jr      nz, .4
        swap    r5
.4:
        and     r5, #%0f
        or      r4, r4
        jr      z, .5
        cp      r7, #1
        jr      eq, .5
        or      r5, r5
        jr      z, .6
        clr     r4
.5:
        ld      %15, r5
        add     %15, #'0'
        call    %0818
.6:
        tm      r7, #1
        jr      z, .7
        inc     r6
.7:
        djnz    r7, .loop
        ld   r5, #%2f
        ld   r6, #8
.pop:
        pop  @r5
        dec  r5
        djnz r6, .pop
        ret

        ; void setCursor@i16@i16
setCursor_Pi16_Pi16:
        ld   %5b, r3
        ld   %5c, r1
        ret

        ; i16 getChar
getChar:
        call %081e
        ld   r0, #0
        ld   r1, %13
        ret

        ; void initRandom@i32
initRandom_Pi32:
        ld   %74, r0
        ld   %75, r1
        ld   %76, r2
        ld   %77, r3
        ld   r0, #%F7
        ld   r1, #%A8
        ld   r2, #%74
        ld   r3, #4
.1:
        ldei @rr0, @r2
        djnz r3, .1
        ret

        ; i16 random16
random16:
        call %0836
        ld   r0, %74
        ld   r1, %75
        and  r0, #%7f
        ret

        ; void waitFrame
waitFrame:
        ld   r0, #0
.loop:
        djnz r0, .loop
        ret

        ; variable 0: tiles[] (u8*/16)
var_0:
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        ; variable 1: moveTiles[] (u8*/16)
var_1:
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        ; variable 2: moveTargets[] (u8*/16)
var_2:
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        ; variable 3: boardFrame[] (u8*/525)
var_3:
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        ; variable 4: displayedFrame[] (u8*/525)
var_4:
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        .data %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00 %00
        ; variable 5: score (i32/4)
var_5:
        .data %00 %00 %00 %00
        ; variable 6: gameState (u8/1)
var_6:
        .data %00

string_0:
        .data "      " %00
string_1:
        .data "   2  " %00
string_2:
        .data "   4  " %00
string_3:
        .data "   8  " %00
string_4:
        .data "  16  " %00
string_5:
        .data "  32  " %00
string_6:
        .data "  64  " %00
string_7:
        .data "  128 " %00
string_8:
        .data "  256 " %00
string_9:
        .data "  512 " %00
string_10:
        .data " 1024 " %00
string_11:
        .data " 2048 " %00
string_12:
        .data "  ?   " %00
string_13:
        .data "+------+" %00
string_14:
        .data "Arrows/WASD  R restart  Esc quit" %00
string_15:
        .data "2048  Score: " %00
string_16:
        .data "          " %00
string_17:
        .data "                                      " %00
string_18:
        .data "2048! Press R to play again." %00
string_19:
        .data "No moves left. Press R to restart." %00

