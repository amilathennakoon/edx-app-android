package org.edx.mobile.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import org.edx.mobile.R;
import org.edx.mobile.databinding.ActivityDiscoveryLaunchBinding;
import org.edx.mobile.module.analytics.Analytics;

public class DiscoveryLaunchActivity extends PresenterActivity<DiscoveryLaunchPresenter, DiscoveryLaunchPresenter.ViewInterface> {

    @NonNull
    @Override
    protected DiscoveryLaunchPresenter createPresenter(@Nullable Bundle savedInstanceState) {
        return new DiscoveryLaunchPresenter(environment.getLoginPrefs(), environment.getConfig().getCourseDiscoveryConfig());
    }

    @NonNull
    @Override
    protected DiscoveryLaunchPresenter.ViewInterface createView(@Nullable Bundle savedInstanceState) {
        final ActivityDiscoveryLaunchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_discovery_launch);
        environment.getAnalyticsRegistry().trackScreenView(Analytics.Screens.LAUNCH_ACTIVITY);
        AuthPanelUtils.setAuthPanelVisible(true, binding.authPanel, environment);
        return new DiscoveryLaunchPresenter.ViewInterface() {
            @Override
            public void setEnabledButtons(boolean courseDiscoveryEnabled) {
                if (courseDiscoveryEnabled) {
                    binding.discoverCourses.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            environment.getAnalyticsRegistry().trackDiscoverCoursesClicked();
                            environment.getRouter().showFindCourses(DiscoveryLaunchActivity.this);
                        }
                    });
                } else {
                    binding.discoverCourses.setVisibility(View.GONE);
                }
            }

            @Override
            public void navigateToMyCourses() {
                finish();
                environment.getRouter().showMyCourses(DiscoveryLaunchActivity.this);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }
}
