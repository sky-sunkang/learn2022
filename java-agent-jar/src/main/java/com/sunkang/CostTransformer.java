package com.sunkang;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class CostTransformer implements ClassFileTransformer {


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        // 这里我们限制下，只针对目标包下进行耗时统计
        if (!className.startsWith("com/sunkang/search/controller")) {
            return classfileBuffer;
        }
        CtClass cl = null;
        System.out.println(className);
        try {
            ClassPool classPool = ClassPool.getDefault();
            cl = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            for (CtMethod method : cl.getDeclaredMethods()) {
                // 所有方法，统计耗时；请注意，需要通过`addLocalVariable`来声明局部变量
                method.addLocalVariable("start", CtClass.longType);
                method.insertBefore("start = System.currentTimeMillis();");
                method.getParameterAnnotations();
                String methodName = method.getLongName();
                method.insertAfter("System.out.println(\"" + methodName + " cost: \" + (System" +
                        ".currentTimeMillis() - start));");
            }
            byte[] transformed = cl.toBytecode();
            return transformed;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return classfileBuffer;
    }
}
