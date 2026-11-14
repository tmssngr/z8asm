:start
go build -trimpath -ldflags="-s -w" ./cmd/z8asm
pause
cls
goto start
