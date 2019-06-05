package com.thecrafter4000.lotrtc.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class TinkersMEClassTransformer implements IClassTransformer {

	public static Map<String, BiConsumer<ClassNode, Boolean>> classes = new HashMap<>();

	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		boolean isObfuscated = !name.equals(transformedName);
		if(classes.containsKey(transformedName)) {
			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(basicClass);
			classReader.accept(classNode, 0);
			System.out.println("TinkersME: Transforming " + transformedName);
			try {
				classes.get(transformedName).accept(classNode, isObfuscated);
				classes.remove(transformedName);
			} catch(Exception e) {
				e.printStackTrace();
				return basicClass;
			}
			ClassWriter classWriter = new ClassWriter(1);
			classNode.accept(classWriter);
			basicClass = classWriter.toByteArray();
		}
		return basicClass;
	}

	static{
		BiConsumer<ClassNode, Boolean> fixButtons = (node, isObf) -> {
			for (FieldNode field : node.fields) {
				if(field.name.equals("background")) {
					field.access = Opcodes.ACC_PUBLIC;
				}
			}

			for (MethodNode method : node.methods) {
				if(method.name.equals("<init>") || method.name.equals("drawButton")) {
					AbstractInsnNode[] instructions = method.instructions.toArray();
					for(int i = 0; i < instructions.length; i++) {
						AbstractInsnNode insn = instructions[i];
						if(insn.getOpcode() == Opcodes.PUTSTATIC || insn.getOpcode() == Opcodes.GETSTATIC) {
							FieldInsnNode fnode = (FieldInsnNode) insn;
							if(fnode.name.equals("background")) {
								if(insn.getOpcode() == Opcodes.GETSTATIC) {
									method.instructions.insertBefore(fnode, new VarInsnNode(Opcodes.ALOAD, 0));
								}else{
									method.instructions.insertBefore(instructions[i-5], new VarInsnNode(Opcodes.ALOAD, 0));
								}
								method.instructions.set(fnode, new FieldInsnNode(fnode.getOpcode()+2, fnode.owner, fnode.name, fnode.desc));
							}
						}
					}
				}
			}
		};

		BiConsumer<ClassNode, Boolean> changeSmelteryBlockCheck = (node, isObf) -> {
			ASMUtils.publicizeMethods(node, methodNode -> methodNode.name.equals("validBlockID") || methodNode.name.equals("validTankID"));
		};

		classes.put("tconstruct.tools.gui.GuiButtonTool", fixButtons);
		classes.put("tconstruct.tools.gui.GuiButtonStencil", fixButtons);
		classes.put("tconstruct.smeltery.logic.SmelteryLogic", changeSmelteryBlockCheck);
	}
}
