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

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.prebid.mobile.testutils.BaseSetup;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Calendar;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = BaseSetup.testSDK)
public class TargetingParamsTest extends BaseSetup {
    @Test
    public void testYearOfBirth() throws Exception {
        // force initial state since it's static might be changed from other tests
        FieldUtils.writeStaticField(TargetingParams.class, "yob", 0, true);
        assertEquals(0, TargetingParams.getYearOfBirth());
        boolean errorThrown1 = false;
        try {
            TargetingParams.setYearOfBirth(-1);
        } catch (Exception e) {
            errorThrown1 = true;
            assertEquals(0, TargetingParams.getYearOfBirth());
        }
        assertTrue(errorThrown1);
        boolean errorThrown2 = false;
        try {
            TargetingParams.setYearOfBirth(Calendar.getInstance().get(Calendar.YEAR) + 5);
        } catch (Exception e) {
            errorThrown2 = true;
            assertEquals(0, TargetingParams.getYearOfBirth());
        }
        assertTrue(errorThrown2);
        int yearOfBirth = Calendar.getInstance().get(Calendar.YEAR) - 20;
        TargetingParams.setYearOfBirth(yearOfBirth);
        assertEquals(yearOfBirth, TargetingParams.getYearOfBirth());
    }

    @Test
    public void testGender() throws Exception {
        TargetingParams.setGender(TargetingParams.GENDER.UNKNOWN);
        assertEquals(TargetingParams.GENDER.UNKNOWN, TargetingParams.getGender());
        TargetingParams.setGender(TargetingParams.GENDER.FEMALE);
        assertEquals(TargetingParams.GENDER.FEMALE, TargetingParams.getGender());
        TargetingParams.setGender(TargetingParams.GENDER.MALE);
        assertEquals(TargetingParams.GENDER.MALE, TargetingParams.getGender());
    }

    @Test
    public void testBundleName() throws Exception {
        FieldUtils.writeStaticField(TargetingParams.class, "bundleName", null, true);
        PrebidMobile.setApplicationContext(activity.getApplicationContext());
        assertEquals("org.robolectric.default", TargetingParams.getBundleName());
        TargetingParams.setBundleName("org.prebid.mobile");
        assertEquals("org.prebid.mobile", TargetingParams.getBundleName());
    }

    @Test
    public void testDomain() throws Exception {
        TargetingParams.setDomain("prebid.org");
        assertEquals("prebid.org", TargetingParams.getDomain());
    }

    @Test
    public void testStoreUrl() throws Exception {
        TargetingParams.setStoreUrl("store://testapp");
        assertEquals("store://testapp", TargetingParams.getStoreUrl());
    }

    @Test
    public void testGDPRFlag() throws Exception {
        PrebidMobile.setApplicationContext(activity.getApplicationContext());
        TargetingParams.setSubjectToGDPR(true);
        assertTrue(TargetingParams.isSubjectToGDPR());
        TargetingParams.setSubjectToGDPR(false);
        assertTrue(!TargetingParams.isSubjectToGDPR());
    }

    @Test
    public void testGDPRConsentString() {
        PrebidMobile.setApplicationContext(activity.getApplicationContext());
        TargetingParams.setGDPRConsentString("testString");
        assertEquals("testString", TargetingParams.getGDPRConsentString());
    }

    @Test
    public void testSetUserKeyword() throws Exception {
        PrebidMobile.setApplicationContext(activity.getApplicationContext());
        FieldUtils.writeStaticField(TargetingParams.class, "userKeywords", new ArrayList<String>(), true);
        TargetingParams.addUserKeyword("key", "value");
        TargetingParams.addUserKeyword("key1", null);
        @SuppressWarnings("unchecked")
        ArrayList<String> keywords = TargetingParams.getUserKeywords();
        assertEquals(2, keywords.size());
        assertEquals("key=value", keywords.get(0));
        assertEquals("key1", keywords.get(1));
        TargetingParams.addUserKeyword("key", "value2");
        assertEquals(3, keywords.size());
        assertEquals("key=value", keywords.get(0));
        assertEquals("key1", keywords.get(1));
        assertEquals("key=value2", keywords.get(2));
        TargetingParams.removeUserKeyword("key");
        assertEquals(1, keywords.size());
        assertEquals("key1", keywords.get(0));
        TargetingParams.clearUserKeywords();
        assertEquals(0, keywords.size());
    }

    @Test
    public void testSetUserKeywords() throws Exception {
        PrebidMobile.setApplicationContext(activity.getApplicationContext());
        FieldUtils.writeStaticField(TargetingParams.class, "userKeywords", new ArrayList<String>(), true);
        TargetingParams.addUserKeyword("key1", "value1");
        String[] values = {"value1", "value2"};
        TargetingParams.addUserKeywords("key2", values);
        @SuppressWarnings("unchecked")
        ArrayList<String> keywords = TargetingParams.getUserKeywords();
        assertEquals(2, keywords.size());
        assertEquals("key2=value1", keywords.get(0));
        assertEquals("key2=value2", keywords.get(1));
        TargetingParams.addUserKeywords("key1", values);
        assertEquals(2, keywords.size());
        assertEquals("key1=value1", keywords.get(0));
        assertEquals("key1=value2", keywords.get(1));
    }

    @Test
    public void testSetInvKeyword() throws Exception {
        PrebidMobile.setApplicationContext(activity.getApplicationContext());
        FieldUtils.writeStaticField(TargetingParams.class, "invKeywords", new ArrayList<String>(), true);
        TargetingParams.addInvKeyword("key", "value");
        TargetingParams.addInvKeyword("key1", null);
        @SuppressWarnings("unchecked")
        ArrayList<String> keywords = TargetingParams.getInvKeywords();
        assertEquals(2, keywords.size());
        assertEquals("key=value", keywords.get(0));
        assertEquals("key1", keywords.get(1));
        TargetingParams.addInvKeyword("key", "value2");
        assertEquals(3, keywords.size());
        assertEquals("key=value", keywords.get(0));
        assertEquals("key1", keywords.get(1));
        assertEquals("key=value2", keywords.get(2));
        TargetingParams.removeInvKeyword("key");
        assertEquals(1, keywords.size());
        assertEquals("key1", keywords.get(0));
        TargetingParams.clearInvKeywords();
        assertEquals(0, keywords.size());
    }

    @Test
    public void testSetInvKeywords() throws Exception {
        PrebidMobile.setApplicationContext(activity.getApplicationContext());
        FieldUtils.writeStaticField(TargetingParams.class, "invKeywords", new ArrayList<String>(), true);
        TargetingParams.addInvKeyword("key1", "value1");
        String[] values = {"value1", "value2"};
        TargetingParams.addInvKeywords("key2", values);
        @SuppressWarnings("unchecked")
        ArrayList<String> keywords = TargetingParams.getInvKeywords();
        assertEquals(2, keywords.size());
        assertEquals("key2=value1", keywords.get(0));
        assertEquals("key2=value2", keywords.get(1));
        TargetingParams.addInvKeywords("key1", values);
        assertEquals(2, keywords.size());
        assertEquals("key1=value1", keywords.get(0));
        assertEquals("key1=value2", keywords.get(1));
    }
}
