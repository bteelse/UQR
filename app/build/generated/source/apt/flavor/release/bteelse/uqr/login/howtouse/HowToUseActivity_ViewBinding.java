// Generated code from Butter Knife. Do not modify!
package bteelse.uqr.login.howtouse;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import bteelse.uqr.R;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HowToUseActivity_ViewBinding<T extends HowToUseActivity> implements Unbinder {
  protected T target;

  @UiThread
  public HowToUseActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.mainViewPager = Utils.findRequiredViewAsType(source, R.id.activity_main_viewPager, "field 'mainViewPager'", ViewPager.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.activity_main_tabLayout, "field 'tabLayout'", TabLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mainViewPager = null;
    target.tabLayout = null;

    this.target = null;
  }
}
