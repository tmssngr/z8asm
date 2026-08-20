	.org %1B94
/*
test:
	call wkey
	ld   %15, %13
	call %0818
	cp   %13, #%40
	jr   nz, test
	ret
*/
wkey:
	push %fd
	srp  #%60
	ld   r0, #%f7
	ld   r1, #%a0
	lde  r2, @rr0
	push r2
	inc  r1
	lde  r2, @rr0
	dec  r1
	lde  @rr0, r2
	clr  %5e
	ld   %5f, #1
	call %18d8
	ld   r0, #%f7
	ld   r1, #%a0
	pop  r2
	lde  @rr0, r2
	jr   .loop1

.restart:
	and  r12, #%7f
.loop1:
	ld   %5f, r13
	ld   %6f, #%1f
	and  %6f, r12
	or   r12, r12
	jr   mi, .loop2
	ld   %6f, #%40
	or   %5f, %5f
	jr   z, .loop2
	or   r12, #%80
.loop2:
	call %081b
	or   r13, r13
	jr   z, .restart

	cp   r13, %5f
	jr   nz, .other
	or   %6f, %6f
	jr   nz, .loop2
	jr   .ret
.other:
	and  r12, #%7f
.ret:
	tm   r12, #%40
	jr   z, .ret2
	call %1Af0     ; beep
.ret2:
	ld   %13, r13
	clr  %5e
	ld   %5f, #1
	call %18d8
	pop  %fd
	ret
