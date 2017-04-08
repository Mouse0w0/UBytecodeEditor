package me.mouse.ube.handler;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import javafx.scene.control.TreeItem;
import me.mouse.ube.warpper.FrameNodeWarpper;

public class FrameHandler implements BytecodeHandler<FrameNodeWarpper>{

	@Override
	public String getText(FrameNodeWarpper item) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("FRAME ");
        switch (item.node.type) {
        case Opcodes.F_NEW:
        case Opcodes.F_FULL:
            sb.append("FULL [");
            appendFrameTypes(nLocal, local);
            sb.append("] [");
            appendFrameTypes(nStack, stack);
            sb.append(']');
            break;
        case Opcodes.F_APPEND:
            sb.append("APPEND [");
            appendFrameTypes(nLocal, local);
            sb.append(']');
            break;
        case Opcodes.F_CHOP:
            sb.append("CHOP ").append(nLocal);
            break;
        case Opcodes.F_SAME:
            sb.append("SAME");
            break;
        case Opcodes.F_SAME1:
            sb.append("SAME1 ");
            appendFrameTypes(1, stack);
            break;
        }
		
		return sb.toString();
	}

	@Override
	public TreeItem<?> getNode(FrameNodeWarpper node) {
		return null;
	}

}
