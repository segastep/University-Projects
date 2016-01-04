;===============================Define DB Strings=======================================
	JMP Start 		; Jump to start so we avoid DB op-code error
	DB B1
        NOP
	DB 9D
	DB "TOP   "		; Created DB with string TOP -starts at RAM location 02
	DB 0			; End of top String
	DB "DOWN  "		; Create DB with string Down -starts at RAM location 05
        DB 0			; End of Down string
	DB "UP    "		; Create DB with string UP staring at ram location
	DB 0			; End of UP string
	DB "BOTTOM"		; Create DB with String Up starting at ram location
	DB 0			; End of bottom string
;-----------------------------------------------------------------------------------------------
; Spaces after strings are used in order not to leave letters when strings have different length
;------------------------------------------------------------------------------------------------
Start:

	
	CLO			; Closes all unwanted windows
	MOV  AL,00		; Clears al for the lift to start functioning
	OUT 06			; Display lift window
	STI

Up: 
	STI
    	IN 06				; Read lift status
    	AND AL,20			; Isolate UP button bit
    	JNZ	PrintUp			; If UP button press go to GoingUp procedure
    	JMP	Up			; If no button or down button is pressed jump back

Down:
	IN 06				; Read lift status
	AND AL,10			; Isolate DOwn bit
	JNZ PrintDown			; if dont button pressed go to Going down
	JMP Down			; If up button or not button is pressed jump back

;--------------------------------GOING UP PROCEDURES AND CHECKS-------------------------------

PrintUp: 
	 
	 PUSH AL			; I push AL to avoid the down button appearing as activee while the lift is going up
	 MOV  DL,13			; Make AL point to the start of UP string
	 CALL 88			; Call print procedure here
	 POP AL				; Restore AL from the stack

GoingUp:
	
	IN 06				; Read lift status
	AND AL,DF			; Clear Up button bit
	OR  AL,01			; Set up motor bit
	OUT 06				; Reset up button and turn on the lift
	OUT 08				; Display numeric keyboard

CheckUp:

	IN 06				; Read lift status
	AND AL,04			; Check if elevator reached top
	JZ CheckUp			; Loop around till top is reached
	MOV AL,00			; Whne top reach moved needed value to stop the lift
	OUT 06				; Stop the lift

PrintTop:
	
	MOV DL,05			; Make AL point to the start of Top string
	CALL   88			; Call print procedure
	JMP Down			; Jump to down - this way you cannot crash the lift on the top



;---------------------------------GOING DOWN PROCEDURES AND CHECKS------------------------------

PrintDown:
	 
         
	 MOV	DL, 0C			; Make AL point to the start of Down string
         CLO                 		; Close unnecassary windows to remove the num pad not 
         OUT 06				; Make the lift visible again
	 CALL 	88			; Call print procedure

GoingDown:
	  
	 IN 06				; Read lift status - may remove this later
	 AND AL,DF			; Clear down Button bit
	 OR  AL,02			; Set down motor bit
	 OUT 06				; Reset down button and turn lift down

CheckDown:
	
	IN 06				; Read lift status
	AND AL,08			; Check lift position
	JZ  CheckDown			; Do this till bottom is reached
	MOV AL,00			; Stop the lift on bottom
	OUT 06				; Lift stopped

PrintBottom:

	MOV DL,1A			; Make AL point to the start of Bottom string
	CALL	88			; Call print procedure
	JMP Up				; Jump to up procedure - now you can only use the up button you cannot crash lift on bottom

;---------------------------------PRINT PROCEDURE-------------------------------

ORG 88				; Starting from 90 to have some space for changes if needed

MoveCl:

	MOV   CL,C0		; Make CL point to the VDU

Print:
	MOV BL,[DL]		; Copy DL aress value to BL
	CMP BL,00		; Check if end of string is reached
	JZ Exit			; If finished exit the loop else continue
	MOV [CL],BL		; Print character
	INC DL			; Inc DL to point to next letter of the string
	INC CL			; Make CL point to the nex VDU position
	JMP Print		; Jump back to print

Exit:
	RET
;------------------------------------NUM KEYBOARD PROCEDURES--------------------------------

ORG 9D

	NOP			; Skip a ram location safety porpose.
        POPF
	STI
CheckEnter:

	IN 08			; Read from Num Keyboard
	CMP AL,0D		; Was the pressed Button Enter ? 
	JNZ CheckUp		; No - Go Back Yes - Continue
	MOV AL,00		; Move needed value to stop the lift
	OUT 06			; Stop the lift
	JMP PrintDown		; Take the lift to bottom
Back: 
OUT 08
popf
RET

END
    
