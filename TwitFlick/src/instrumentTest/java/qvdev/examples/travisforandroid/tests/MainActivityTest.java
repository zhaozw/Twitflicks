package qvdev.examples.travisforandroid.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.qvdev.apps.twitflick.View.DetailView;

/**
 * Created by QVDev on 7/3/13.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<DetailView> {

    public MainActivityTest() {
        super(DetailView.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testActivityCreation() {
        assertNotNull("Activity is null, check creating of activity", getActivity());
    }
}
