# Cooperative Testing Initiative

meg
boot $t1, 5  		#Initiate Blue with 5 points
boot $t2, 5  		#Initiate Orange with 5 points

tl $t2, $t1, $t2 	#Orange gains 5 trust points

bp  			#Jumps to op

sci $t1, $t2, $t1  	#Random math on Blue
sb $t1, $t2, door  	#Jumps to door if equal

op  			#Jumps to bp

door:
msp $t2  		#Tries to crush Orange (50% chance)
sb $t1, $t2, end  	#Skip kill if orange survives
kill  			#Orange was crushed, end program early

end:  			#Orange not crushed, cake time!
cube  			#Prints an encouraging phrase

pdv $t1, $t1, 14  	#Grants 14 point to blue
pdv $t2, $t2, 19  	#19 points to orange