package mars.mips.instructions.customlangs;
import mars.simulator.*;
import mars.mips.hardware.*;
import mars.mips.instructions.syscalls.*;
import mars.*;
import mars.util.*;
import java.util.*;
import java.io.*;
import mars.mips.instructions.*;
import java.util.Random;

public class PortalAssembly extends CustomAssembly{

    // I found these are necessary to track when a portal is used, and what line it is on
    public static Integer bluePortalAddy = null;
    public static Integer orangePortalAddy = null;

    @Override
    public String getName(){
        return "Portal Assembly";
    }

    @Override
    public String getDescription(){
        return "An assembly language meant for doing what we must because we can.";
    }

    @Override
    protected void populate(){
        instructionList.add(
                new BasicInstruction("boot $t1, 100",
                        "Boot Sequence: Sets [$t1] to an immediate value. \"Powerup initiated.\"",
                        BasicInstructionFormat.I_FORMAT,
                        "000001 fffff 00000 ssssssssssssssss",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int set = operands[1] << 16 >> 16;

                                RegisterFile.updateRegister(operands[0], set);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("pdv $t1, $t1, 100",
                        "Pneumatic Diversity Vent: Adds an immediate value to [$t1]. Can also be used to add other register's values to another. \"It's your old friend, deadly neurotoxin. If I were you, I'd take a deep breath. And hold it.\"\n",
                        BasicInstructionFormat.I_FORMAT,
                        "000010 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int add1 = RegisterFile.getValue(operands[1]);
                                int add2 = operands[2] << 16 >> 16;
                                int sum = add1 + add2;

                                RegisterFile.updateRegister(operands[0], sum);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("taunt $t1, 50",
                        "Taunt: Subtracts an immediate value from [$t1] and prints a message. \"For that, Blue is penalized fifty science collaboration points.\"",
                        BasicInstructionFormat.I_FORMAT,
                        "000011 fffff 00000 ssssssssssssssss",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int val1 = RegisterFile.getValue(operands[0]);
                                int val2 = operands[1] << 16 >> 16;
                                int result = val1 - val2;

                                RegisterFile.updateRegister(operands[0], result);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("rgel $t1, 5",
                        "Repulsion Gel: Multiplies the value in [$t1] by an immediate value. \"We haven't entirely nailed down what element it is yet, but I'll tell you this: it's a lively one, and it does NOT like the human skeleton.\"",
                        BasicInstructionFormat.I_FORMAT,
                        "000100 fffff 00000 ssssssssssssssss",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int val1 = RegisterFile.getValue(operands[0]);
                                int val2 = operands[1] << 16 >> 16;
                                int result = val1 * val2;

                                RegisterFile.updateRegister(operands[0], result);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("msp $t1",
                        "Mashy Spike Plate: Has a 50% chance to set the value of [$t1] to 0. \"Still a work in progress, don't judge me yet. Eventually I'd like to get them to sort of shoot fire at you, moments before crushing you.\"",
                        BasicInstructionFormat.I_FORMAT,
                        "000101 fffff 00000 0000000000000000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                Random random = new Random();
                                int crush = random.nextInt(2);

                                if (crush == 1) {
                                    RegisterFile.updateRegister(operands[0], 0);
                                }
                            }
                        }));

        instructionList.add(
                new BasicInstruction("sb $t1,$t2,label",
                        "Heavy Duty Super-Colliding Super Button: Jumps to the label if the values in [$t1] and [$t2] are equal. \"Please place the Weighted Storage Cube on the 1500 Megawatt Aperture Science Heavy Duty Super-Colliding Super Button.\"",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "000110 fffff sssss tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();

                                if (RegisterFile.getValue(operands[0])
                                        == RegisterFile.getValue(operands[1]))
                                {
                                    Globals.instructionSet.processBranch(operands[2]);
                                }
                            }
                        }));

        instructionList.add(
                new BasicInstruction("trl $t1,$t2,label",
                        "Turret Redemption Line: Jumps to the label if the values in  [$t1] and [$t2] are NOT equal. \"Rest assured that all lethal military androids have been taught to read and provided with one copy of the Laws of Robotics. To share.\"",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "000111 fffff sssss tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                if (RegisterFile.getValue(operands[0])
                                        != RegisterFile.getValue(operands[1]))
                                {
                                    Globals.instructionSet.processBranch(operands[2]);
                                }
                            }
                        }));

        instructionList.add(
                new BasicInstruction("afp label",
                        "Aerial Faith Plate: Jumps to the given label. \"Well. Have fun soaring through the air without a care in the world.\"",
                        BasicInstructionFormat.J_FORMAT,
                        "001000 ffffffffffffffffffffffffff",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                Globals.instructionSet.processJump(
                                        ((RegisterFile.getProgramCounter() & 0xF0000000)
                                                | (operands[0] << 2)));
                            }
                        }));

        instructionList.add(
                new BasicInstruction("tl $t2,$t1,$t2",
                        "Trust Lesson: Sets [$t2] to ([$t1] plus [$t2]). \"Orange just taught blue a valuable lesson in trust. For that, orange receives 17 science collaboration points.\"",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 000001",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int add1 = RegisterFile.getValue(operands[1]);
                                int add2 = RegisterFile.getValue(operands[2]);
                                int sum = add1 + add2;
                                // overflow on A+B detected when A and B have same sign and A+B has other sign.
                                if ((add1 >= 0 && add2 >= 0 && sum < 0)
                                        || (add1 < 0 && add2 < 0 && sum >= 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], sum);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("kill",
                        "The Part Where He Kills You: This is that part. Ends the program.",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 00000 00000 00000 00000 000000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                throw new ProcessingException(statement, "This is the part where he kills us!\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("bp",
                        "Blue Portal: Jumps to the nearest orange portal. \"Remember: you're looking for a gun that makes holes. Not bullet holes, but-- well, you'll figure it out.\"",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 00000 00000 00000 00000 001010",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int currentAddress = statement.getAddress();

                                if (orangePortalAddy != null && currentAddress != orangePortalAddy) {
                                    Globals.instructionSet.processJump(orangePortalAddy);
                                } else {
                                    bluePortalAddy = currentAddress;
                                    //SystemIO.printString("Blue portal placed at: " + currentAddress + "\n");
                                    // Enable for debug
                                }
                            }
                        }));

        instructionList.add(
                new BasicInstruction("op",
                        "Orange Portal: Jumps to the nearest blue portal. \"Good. You have a dual portal device. There should be a way back to the testing area up ahead.\"",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 00000 00000 00000 00000 001011",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int currentAddress = statement.getAddress();

                                if (bluePortalAddy != null && currentAddress != bluePortalAddy) {
                                    Globals.instructionSet.processJump(bluePortalAddy);
                                } else {
                                    orangePortalAddy = currentAddress;
                                    //SystemIO.printString("Orange portal placed at: " + currentAddress + "\n");
                                    // Enable for debug
                                }
                            }
                        }));

        instructionList.add(
                new BasicInstruction("sci $t3, $t2, $t1",
                        "For Science!: Performs a random operation between [$t2] and [$t1], and stores the result in [$t3]. \"I'll be honest, we're throwing science at the wall here to see what sticks. No idea what it'll do.\"",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 000010",
                        new SimulationCode()
                        {

                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {

                                int[] operands = statement.getOperands();
                                int val1 = RegisterFile.getValue(operands[1]);
                                int val2 = RegisterFile.getValue(operands[2]);

                                int result;

                                Random random = new Random();
                                int roll = random.nextInt(6);

                                // Uses generated random number to perform funky math
                                switch (roll){
                                    case 0:
                                        result = val1 + val2;
                                        break;
                                    case 1:
                                        result = val1 - val2;
                                        break;
                                    case 2:
                                        result = val1 / val2;
                                        break;
                                    case 3:
                                        result = val1 * val2;
                                        break;
                                    case 4:
                                        result = val1 + (val2 / 2) + 3;
                                        break;
                                    case 5:
                                        result = val1 - (val2 + 3);
                                        break;
                                    default:
                                        SystemIO.printString("Testing failure!\n");
                                        result = 0;
                                }

                                RegisterFile.updateRegister(operands[0], result);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("clap $t3",
                        "Potato Battery: Adds 3 claps to [$t3], each clap is also worth 1 Science Collaboration Point. \"Oh good, my slow clap processor made it into this thing, so we have that.\"",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 fffff 00000 00000 00000 000011",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int val1 = RegisterFile.getValue(operands[0]);

                                int sum = val1 + 3;

                                RegisterFile.updateRegister(operands[0], sum);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("cube",
                        "Companion Cube: Prints a random \"encouraging\" phrase. \"I think that one was about to say 'I love you.' They ARE sentient, of course.\"",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 00000 00000 00000 00000 000100",
                        new SimulationCode()
                        {

                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {

                                // Generates a random number and prints a statement based on it
                                Random random = new Random();
                                int roll = random.nextInt(6);


                                switch (roll){
                                    case 0:
                                        SystemIO.printString("\"I love you!\"\n");
                                        break;
                                    case 1:
                                        SystemIO.printString("\"Hooray!\"\n");
                                        break;
                                    case 2:
                                        SystemIO.printString("\"I'm different.\"\n");
                                        break;
                                    case 3:
                                        SystemIO.printString("\"Now we can be friends!\"\n");
                                        break;
                                    case 4:
                                        SystemIO.printString("\"Hello, friend!\"\n");
                                        break;
                                    case 5:
                                        SystemIO.printString("\"Keep it up!\"\n");
                                        break;
                                    default:
                                        SystemIO.printString("the cubes don't have voices...\n");
                                }
                            }
                        }));

        instructionList.add(
                new BasicInstruction("fire $t1, $t2",
                        "Turret: Compares the values in [$t1] and [$t2] and divides the higher value by 2 (turret AI is trained to fire on higher value targets, and getting shot results in a reduction of Science Collaboration Points). \"Target acquired.\"",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 fffff sssss 00000 00000 000101",
                        new SimulationCode()
                        {

                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int val1 = RegisterFile.getValue(operands[0]);
                                int val2 = RegisterFile.getValue(operands[1]);

                                SystemIO.printString("\"Target Acquired\"\n");

                                if (val1 > val2) {      // Compares values and divides higher value by 2
                                    RegisterFile.updateRegister(operands[0], (val1 / 2));
                                } else {
                                    RegisterFile.updateRegister(operands[1], (val2 / 2));
                                }

                                }
                            }
                        ));

        instructionList.add(
                new BasicInstruction("dox $t1",
                    "Paradox: Performs random operations on [$t1] until its value is below 0. \"This. Sentence. Is. FALSE.\"",
                    BasicInstructionFormat.R_FORMAT,
                    "000000 fffff 00000 00000 00000 000110",
                    new SimulationCode()
    {

        public void simulate(ProgramStatement statement) throws ProcessingException
        {

            int[] operands = statement.getOperands();
            int val1 = RegisterFile.getValue(operands[0]);

            int result = val1;

            while (result >= 0) {

                Random random = new Random();
                int roll = random.nextInt(6);

                // Uses generated random number to perform funky math
                switch (roll) {
                    case 0:
                        result += val1;
                        break;
                    case 1:
                        result -= val1;
                        break;
                    case 2:
                        result /= val1;
                        break;
                    case 3:
                        result *= val1;
                        break;
                    case 4:
                        result += val1 - 17;
                        break;
                    case 5:
                        result -= (result - val1);
                        break;
                    default:
                        SystemIO.printString("Paradox failure!\n");
                }
            }

            RegisterFile.updateRegister(operands[0], result);
        }
    }));

        instructionList.add(
                new BasicInstruction("meg",
                    "Material Emancipation Grill: Makes future portal instructions ignore other portal instructions before this instruction. \"Please be advised that a noticeable taste of blood is not part of any test protocol but is an unintended side effect of the Aperture Science Material Emancipation Grill…\"",
                    BasicInstructionFormat.R_FORMAT,
                    "000000 00000 00000 00000 00000 000111",
                    new SimulationCode()
    {
        public void simulate(ProgramStatement statement) throws ProcessingException
        {
                      bluePortalAddy = null;
                      orangePortalAddy = null;
        }
    }));

        instructionList.add(
                new BasicInstruction("cc $t3",
                    "Core Corruption: Adds 20 to [$t3]. \"Warning: Central core is eighty percent corrupt.\"",
                    BasicInstructionFormat.R_FORMAT,
                    "000000 fffff 00000 00000 00000 001000",
                    new SimulationCode()
    {
        public void simulate(ProgramStatement statement) throws ProcessingException
        {
            int[] operands = statement.getOperands();
            int val1 = RegisterFile.getValue(operands[0]);

            int sum = val1 + 20;

            RegisterFile.updateRegister(operands[0], sum);
        }
    }));

        instructionList.add(
                new BasicInstruction("srb $t1, $t2, $t3",
                    "Stalemate Resolution Button: Swaps the values of [$t1] and [$t2] if [$t3] is greater than or equal to 80. \"Stalemate Resolution Associate: Please press the Stalemate Resolution Button.\"",
                    BasicInstructionFormat.R_FORMAT,
                    "000000 sssss ttttt fffff 00000 001001",
                    new SimulationCode()
    {
        public void simulate(ProgramStatement statement) throws ProcessingException
        {
            int[] operands = statement.getOperands();
            int val1 = RegisterFile.getValue(operands[0]);
            int val2 = RegisterFile.getValue(operands[1]);
            int val3 = RegisterFile.getValue(operands[2]);

            if (val3 >= 80) {
                RegisterFile.updateRegister(operands[0], val2);
                RegisterFile.updateRegister(operands[1], val1);
            }

        }
    }));


    }
}