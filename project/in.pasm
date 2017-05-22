NOP
LOD  1AC
LOD  [B1]
LODI  ABC
STO  3BB
STO  [2A] wrong 
ADD  25
ADD  [AA]
ADDI  5
SUB  [7FF]
SUB  700
SUBI  -12345
MUL  [1]
MUL  0
MULI  1000
DIV  123
DIV  [123] wrong 
DIVI  3
DIVI  0
AND  123
AND  [123] wrong 
ANDI  -10
NOT wrong
CMPL  [8A] wrong 
CMPL  A8
CMPZ  8A
CMPZ  [A8] wrong 
JUMP  100
JUMP  [100] wrong 
JUMPI  -15
JUMPA  2FF
JMPZ  100
JMPZ  [100] wrong 
JMPZI  100
JMPZA  15
HALT wrong 
---------
4FF 6A
0 FF
10 -A9