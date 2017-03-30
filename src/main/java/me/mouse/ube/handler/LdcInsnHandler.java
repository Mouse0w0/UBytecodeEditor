package me.mouse.ube.handler;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import me.mouse.ube.warpper.LdcInsnNodeWarpper;

public class LdcInsnHandler implements BytecodeHandler<LdcInsnNodeWarpper>{

	@Override
	public String getText(LdcInsnNodeWarpper item, ClassNode root) {
		StringBuilder sb = new StringBuilder("LDC ");
		
        if (item.node.cst instanceof String) {
            sb.append("\"").append(item.node.cst).append("\"");
        } else if (item.node.cst instanceof Type) {
            sb.append(((Type) item.node.cst).getDescriptor()).append(".class");
        } else {
            sb.append(item.node.cst);
        }
        
		return sb.toString();
	}

}
