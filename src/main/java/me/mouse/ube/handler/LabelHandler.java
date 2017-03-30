package me.mouse.ube.handler;

import org.objectweb.asm.tree.ClassNode;
import me.mouse.ube.warpper.LabelNodeWarpper;

public class LabelHandler implements BytecodeHandler<LabelNodeWarpper>{

	@Override
	public String getText(LabelNodeWarpper item, ClassNode root) {
        String name = item.parent.labelNames.get(item.node.getLabel());
        if (name == null) {
            name = "L" + item.parent.labelNames.size();
            item.parent.labelNames.put(item.node.getLabel(), name);
        }
		return name;
	}


}
