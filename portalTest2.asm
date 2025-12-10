# Stalemate Resolution Associate Training

meg
boot $t1, 24  		#Load 24 to Blue
boot $t2, 36  		#Load 36 to Orange

afp lair  		#Literally jump to the lair

bp  			#Portal to Orange Portal

lair:  			#Enter lair here
cc $t3  		#Core corrupts 20% each loop
srb $t1, $t2, $t3  	#Swaps points if cc > 80%
sb $t1, $t2, transfer	#Detects equal points
clap $t1		#Blue gains 3 points each loop

op  			#Portal to Blue Portal

transfer:  		#Exit lair here
dox $t3  		#After Core transfer, use a paradox
         		#to wipe refused Core