/*
 * QAuxiliary - An Xposed module for QQ/TIM
 * Copyright (C) 2019-2023 QAuxiliary developers
 * https://github.com/cinit/QAuxiliary
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the qwq233 Universal License
 * as published on https://github.com/qwq233/license; either
 * version 2 of the License, or any later version and our EULA as published
 * by QAuxiliary contributors.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the qwq233 Universal License for more details.
 *
 * See
 * <https://github.com/qwq233/license>
 * <https://github.com/cinit/QAuxiliary/blob/master/LICENSE.md>.
 */

package cc.hicore.message.chat;

import android.text.TextUtils;
import cc.hicore.QApp.QAppUtils;
import cc.hicore.ReflectUtil.MField;
import cc.hicore.ReflectUtil.XField;
import cc.hicore.Utils.XLog;
import com.tencent.qqnt.kernel.nativeinterface.Contact;
import io.github.qauxv.bridge.SessionInfoImpl;
import io.github.qauxv.util.Initiator;
import java.lang.reflect.Field;

public class SessionUtils {
    public static Contact AIOParam2Contact(Object AIOParam) {
        try {
            Object AIOSession = XField.obj(AIOParam).type(Initiator.loadClass("com.tencent.aio.data.AIOSession")).get();
            Object AIOContact = XField.obj(AIOSession).type(Initiator.loadClass("com.tencent.aio.data.AIOContact")).get();
            Contact contact = new Contact();
            contact.setPeerUid(getCurrentPeerIDByAIOContact(AIOContact));

            int chatType = getCurrentChatTypeByAIOContact(AIOContact);
            contact.setChatType(chatType);

            if (chatType == 4){
                contact.setGuildId(getCurrentGuildIDByAIOContact(AIOContact));
            }
            return contact;
        }catch (Exception e){
            XLog.e("SessionUtils.AIOParam2Contact",e);
            return null;
        }

    }
    public static String getCurrentPeerIDByAIOContact(Object AIOContact) throws Exception {
        return XField.obj(AIOContact).name("f").type(String.class).get();
    }
    public static int getCurrentChatTypeByAIOContact(Object AIOContact) throws Exception{
        return XField.obj(AIOContact).name("e").type(int.class).get();
    }
    public static String getCurrentGuildIDByAIOContact(Object AIOContact) throws Exception{
        return XField.obj(AIOContact).name("g").type(String.class).get();
    }
}
