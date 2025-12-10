Welcome to Portal Assembly! Get ready to start thinking with portals...

I've taken the liberty of implementing all 20 instructions detailed in my outline, though some with a few quirks.

The following are the 10 basic instructions (msp might not be basic but who cares, really):

Boot Sequence                               boot
Pneumatic Diversity Vent                    pdv
Taunt                                       taunt
Repulsion Gel                               rgel
Mashy Spike Plate                           msp
Heavy Duty Super-Colliding Super Button     sb
Turret Redemption Line                      trl
Aerial Faith Plate                          afp
Trust Lesson                                tl
The Part Where He Kills You                 kill


Here are the creative instructions:

Blue Portal                                 bp
Orange Portal                               op
For Science!                                sci
Potato Battery                              clap
Companion Cube                              cube
Turret                                      fire
Paradox                                     dox
Material Emancipation Grill                 meg
Core Corruption                             cc
Stalemate Resolution Button                 srb

Their functions are detailed in my awesome Google Drawing for module 2 (anyone who knows a lick of typography will have my head).
But descriptions for every instruction can be found within the Java code and in the MARS LE help section.

In order to make the language usable in MARS LE:

1) Use the terminal and navigate to the MARS LE folder on your PC
2) Use this command to build a .jar file to be used in MARS:  java -jar BuildCustomLang.jar PortalAssembly.java
3) Open MARS LE and open the language switcher from the tools dropdown menu
4) Select Portal Assembly and strap on your long-fall boots
5) ?????
5) Profit

Also, keep in mind that the first portal instruction encountered after an Emancipation Grill will NOT jump to its counterpart.
This is because the other portal's register hasn't been read yet, and there's not much I can really do about that.
So basically, just code around that little quirk, and everything will be fine.

One more thing, I recommend having an Emancipation Grill at the start of all of your programs (they do this in the game, too)
because if you don't, the saved portal addresses won't reset by themselves, and it causes problems when running a program more
than once.

TL;DR put meg at the top or else thing go boom.


Anyway, that's about all there is to it. Happy portal-ing!