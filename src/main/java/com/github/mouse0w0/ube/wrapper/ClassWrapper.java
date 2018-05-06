package com.github.mouse0w0.ube.wrapper;

import static com.github.mouse0w0.ube.util.BytecodeUtils.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.github.mouse0w0.ube.UBytecodeEditor;
import com.github.mouse0w0.ube.util.BytecodeUtils;

import javafx.scene.control.TreeItem;

public class ClassWrapper extends NodeWrapperBase<Path>{
	
	private ClassNode classNode;
	
	public ClassWrapper(Path source, PackageWrapper parent) {
		super(source, parent);
		try {
			classNode = BytecodeUtils.loadClassNode(source);
		} catch (IOException e) {
			UBytecodeEditor.LOGGER.error(e.getMessage(), e);
		}
	}
	

	public ClassNode getClassNode() {
		return classNode;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getText() {
		ClassNode node = getClassNode();
		StringBuilder sb = new StringBuilder();
		
		sb.append(getAccess(node.access & ~Opcodes.ACC_SUPER));
		if ((node.access & Opcodes.ACC_ANNOTATION) != 0)
			sb.append("@interface ");
		else if ((node.access & Opcodes.ACC_INTERFACE) != 0)
			sb.append("interface ");
		else if ((node.access & Opcodes.ACC_ENUM) == 0)
			sb.append("class ");

		sb.append(getRealName(node.name));

		if (node.superName != null && !"java/lang/Object".equals(node.superName))
			sb.append(" extends ").append(getRealName(node.superName)).append(' ');

		if (node.interfaces != null && node.interfaces.size() > 0) {
			sb.append(" implements ");
			for (String inter : (List<String>) node.interfaces) {
				sb.append(getRealName(inter)).append(',');
			}
		}
		return sb.toString();
	}


	@SuppressWarnings("unchecked")
	@Override
	protected TreeItem<NodeWrapper<?>> createTreeItem() {
		TreeItem<NodeWrapper<?>> item = new TreeItem<NodeWrapper<?>>(this);
		ClassNode cn = getClassNode();
		if(cn.visibleAnnotations != null)
			for(AnnotationNode an : (List<AnnotationNode>)cn.visibleAnnotations) {
				item.getChildren().add(new AnnotationWrapper(an, this).getTreeItem());
			}
		if(cn.fields != null)
			for (FieldNode fn : (List<FieldNode>) cn.fields) {
				item.getChildren().add(new FieldWrapper(fn, this).getTreeItem());
			}
		if(cn.methods != null)
			for (MethodNode mn : (List<MethodNode>) cn.methods) {
				item.getChildren().add(new MethodWrapper(mn, this).getTreeItem());
			}
		return item;
	}

}
