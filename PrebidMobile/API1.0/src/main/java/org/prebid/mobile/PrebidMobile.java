/*
 *    Copyright 2018-2019 Prebid.org, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.prebid.mobile;

import android.content.Context;

import java.lang.ref.WeakReference;

public class PrebidMobile {

    public static final int TIMEOUT_MILLIS = 2_000;

    static int timeoutMillis = TIMEOUT_MILLIS; // by default use 20000 milliseconds as timeout
    static boolean timeoutMillisUpdated = false;

    private PrebidMobile() {
    }

    private static String accountId = "";

    public static void setPrebidServerAccountId(String accountId) {
        PrebidMobile.accountId = accountId;
    }

    public static String getPrebidServerAccountId() {
        return accountId;
    }

    private static Host host = Host.APPNEXUS;

    public static void setPrebidServerHost(Host host) {
        PrebidMobile.host = host;
        timeoutMillisUpdated = false; // each time a developer sets a new Host for the SDK, we should re-calculate the time out millis
        timeoutMillis = TIMEOUT_MILLIS;
    }

    public static Host getPrebidServerHost() {
        return host;
    }

    private static boolean shareGeoLocation = false;

    public static void setShareGeoLocation(boolean share) {
        PrebidMobile.shareGeoLocation = share;
    }

    public static boolean isShareGeoLocation() {
        return shareGeoLocation;
    }

    private static WeakReference<Context> applicationContextWeak;

    public static void setApplicationContext(Context context) {
        applicationContextWeak = new WeakReference<Context>(context);
    }

    public static Context getApplicationContext() {
        if (applicationContextWeak != null) {
            return applicationContextWeak.get();
        }
        return null;
    }
}
