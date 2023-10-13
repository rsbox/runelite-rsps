package net.runelite.client.plugins.privateserver;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.RuneLite;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.math.BigInteger;

@PluginDescriptor(
        name = "Private Server",
        description = "For RSPS Use ONly"
)
public class PrivateServerPlugin extends Plugin {

    @Inject
    private Client client;

    @Override
    public void startUp() {
        try {
            Class<?> klass = RuneLite.clientClassLoader.loadClass("cl");
            Field field = klass.getDeclaredField("al");
            field.setAccessible(true);
            BigInteger origValue = (BigInteger) field.get(null);
            setStaticField(field, new BigInteger("c2ad7c07cb1c136b0bf3639f0c2f1c3d8a37bf59691db99276e8642ef0a3df1d6871b0c43f25745fbe1045396b6628765047135b30f9e3fb90775fd4983ef67ada011e4e57a8457e66b730f3cc13a32bd8cf475ddbc9b5616f955f93a3f8b9eab63061754b2e27856ee19482c06e8e6ddfa4094ccb91ab0b9ae34ec4c2b08c35", 16));
            BigInteger newValue = (BigInteger) field.get(null);
            System.out.println("Old RSA: " + origValue.toString(16));
            System.out.println("New RSA: " + newValue.toString(16));
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void setStaticField(Field field, Object value) {
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) unsafeField.get(null);
            Object base = unsafe.staticFieldBase(field);
            long offset = unsafe.staticFieldOffset(field);
            unsafe.putObject(base, offset, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
