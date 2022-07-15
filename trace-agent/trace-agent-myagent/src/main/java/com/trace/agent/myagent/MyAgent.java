package com.trace.agent.myagent;

//import com.alibaba.ttl.javassist.ClassPool;
//import com.alibaba.ttl.javassist.CtClass;
//import com.alibaba.ttl.javassist.CtMethod;

import com.alibaba.ttl.threadpool.agent.internal.javassist.ClassPool;
import com.alibaba.ttl.threadpool.agent.internal.javassist.CtClass;
import com.alibaba.ttl.threadpool.agent.internal.javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class MyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("load TTLMDCAdapter...");

        inst.addTransformer(new TransformMDCMain());
    }

    private static class TransformMDCMain implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

            if (!className.equals("org/slf4j/MDC")) {
                return classfileBuffer;
            }

            try {
                ClassPool cp = ClassPool.getDefault();
                CtClass ctClass = cp.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod ctMethod = ctClass.getDeclaredMethod("bwCompatibleGetMDCAdapterFromBinder");
                ctMethod.setBody("{System.out.println(\"=================loading TtlMDCAdapter===============\");" +
                        "try {return org.slf4j.TtlMDCAdapter.getInstance();} catch (NoSuchMethodError nsme) {return org.slf4j.TtlMDCAdapter.getInstance();}}");
                return ctClass.toBytecode();
            } catch (Throwable e) {
                e.printStackTrace();
                System.out.println("loading TtlMDCAdapter fail...");
                return classfileBuffer;
            }
        }
    }
}