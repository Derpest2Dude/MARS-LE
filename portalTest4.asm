# Enrichment Sphere Testing

meg
boot $t1, 100  		#Boot with 100 Blue points
boot $t2, 60  		#Boot 60 orange points

bp  			#Portal to orange portal
loop:  

taunt $t1, 5  		#Subtracting 5 points from Blue
sb $t1, $t2, next  	#Go next if Blue has 60

op  			#Portal to blue portal
next:  			#Exit loop here

sb $t3, $t1, catwalk	#When $t3 = 60, jump
rgel $t1, 2  		#Double Blue’s points
clap $t3  		#Adds 3 to $t3 every several loops
trl $t1, $t2, loop  	#Enters loop again if points aren't equal

catwalk:

cube