package cs.eng1.tests;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import com.badlogic.gdx.Gdx;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    /**
     * This test is here to make sure the testing environment is not broken.
     */
    public void alwaysTrueTest() {
        assertTrue("This test should pass every time", true);
    }

    @Test
    public void bucketAssetTest() {
        // System.out.println(Gdx.files.internal("bucket.png"));
        assertTrue("Passes if test asset is loaded", Gdx.files.internal("badlogic.jpg").exists());
    }

}
