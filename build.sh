#!/bin/bash
while :; do
  go build -trimpath -ldflags="-s -w" ./cmd/z8asm
  echo "Press Enter to continue, or Ctrl+C to quit."
  read -r
  clear
done
