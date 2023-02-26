package cs.eng1.tests;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.glutils.FileTextureData;

import static org.junit.Assert.*;

import cs.eng1.piazzapanic.chef.FixedStack;
import cs.eng1.piazzapanic.food.FoodTextureManager;

@RunWith(GdxTestRunner.class)
public class FixedStackTests {
    FixedStack<Integer> fixedStack = new FixedStack<Integer>(5);

    // Tests if the stack's max size is properly assigned
    @Test
    public void fixedStackLengthTest() {
        assertEquals("The stack has a max size equal to its size.", 5, fixedStack.maxSize);
    }

    // Tests for hasSpace function being able to see space
    @Test
    public void hasSpaceTest() {
        FixedStack<Integer> fixedStack = new FixedStack<Integer>(1);
        assertTrue("hasSpace is true when the stack isn't full", fixedStack.hasSpace());
    }

    @Test
    public void hasNoSpaceTest() {
        FixedStack<Integer> fixedStack = new FixedStack<Integer>(0);
        assertFalse("hasSpace is false when at max size", fixedStack.hasSpace());
    }

    // Tests push for a range of values towars the maximum size
    @Test
    public void pushWithSpaceTest() {
        FixedStack<Integer> fixedStack = new FixedStack<Integer>(5);
        for (int i = 1; i < 6; i++) {
            fixedStack.push(1);
            assertEquals("Push should work when the size is less than maxSize", i, fixedStack.size());
        }
    }

    @Test
    public void pushWithoutSpaceTest() {
        FixedStack<Integer> fixedStack = new FixedStack<Integer>(0);
        fixedStack.push(1);
        assertEquals("Push should not work when the size is equal to maxSize", 0, fixedStack.size());
    }
}