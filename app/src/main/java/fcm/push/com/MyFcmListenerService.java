package fcm.push.com;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by srikantht on 9/6/17.
 */
public class MyFcmListenerService extends FirebaseMessagingService {
    private static final String TAG = MyFcmListenerService.class.getSimpleName();


    @Override
    public void onMessageReceived(RemoteMessage message) {
        Log.i(TAG, "onMessageReceived");
        sendNotification("Test FCM Push");
            Map<String, String> data = message.getData();
        String from = message.getFrom();
        Log.i(TAG, "from : " + from);
        Log.i(TAG, "to : " + message.getTo());
        Log.i(TAG, "id : " + message.getMessageId());
        Log.i(TAG, "type : " + message.getMessageType());
        String GCMmessage = null;

        if (data != null) {
            JSONObject dataJson = new JSONObject(data);
            if (dataJson != null && dataJson.has("body")) {
                Log.i(TAG,"dataJson "+dataJson.toString());
                try {
                    GCMmessage = dataJson.getString("body");
//                    Log.i(TAG,"bodyJson "+bodyJson.toString());
//                    GCMmessage = bodyJson.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

//        if(message.getNotification() != null && message.getNotification().getBody() != null){
//            Log.i(TAG, "title : " + message.getNotification().getTitle());
//            Log.i(TAG, "body : " + message.getNotification().getBody());
//            GCMmessage =  message.getNotification().getBody().toString();
//        }
        if (GCMmessage != null) {
            Log.i(TAG, "PN Message : " + GCMmessage);
        }

        String result = null;
        if (GCMmessage != null)
//            result = processPushNotification(GCMmessage);

        // notifies user
        if (result != null && GCMmessage != null) {
            Log.d(TAG, "result : " + result);
            Log.d(TAG, "GCMmessage : " + GCMmessage.toString());
//            TrayApplication app = (TrayApplication) getApplication();
//            app.generateNotification("", result, Integer.parseInt("1"), GCMmessage, null);
        }
    }

    /**
     * To process push notification message
     *
     * @param message
     * @return
     */
//    private String processPushNotification(String message) {
//        TrayApplication app = (TrayApplication) getApplication();
//        try {
//            JSONObject json = new JSONObject(message);
//            if (json.has("messageType")) {
//
//                String messageType = json.getString("messageType");
//
//                if (messageType == null)
//                    return message;
//
//                if (messageType.equals("updateOrderStatus")) {
//
//                    // Handle updateOrderStatus from Push Notification
//                    if (!app.isStaffUser() || app.isKioskMode())
//                        app.syncOrders();
//                    else
//                        app.notifyObservers(TrayApplication.ORDERS_UPDATED);
//
//                } else if (messageType.equals("orderTimeout")) {
//
//                    // Handle orderTimeout from Push Notification. Time Out is based on venue configuration
//                    if (!app.isStaffUser() || app.isKioskMode())
//                        app.syncOrders();
//                    else
//                        app.notifyObservers(TrayApplication.ORDERS_UPDATED);
//
//                } else if (messageType.equals("DrinkOffered")) {
//
//                    // Process offered drink order
//                    if (app.isStaffUser()) {
//
//                        // Message alert
//                        Intent intent = new Intent(app, CustomAlertActivity.class);
//                        intent.putExtra("title", "Free Gift");
//                        intent.putExtra("body", "You've received a free gift from someone");
//                        intent.putExtra("action", "AcceptGift");
//                        intent.putExtra("orderId", json.getString("orderId"));
//                        intent.putExtra("senderId", json.getString("senderBartsyId"));
//                        intent.putExtra("receiverId", json.getString("bartsyId"));
//                        intent.putExtra("positive", "See Details");
//                        intent.putExtra("negative", "Ignore");
//                        intent.putExtra("timeoutMillis", Constants.screenTimeoutVeryLong);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        app.startActivity(intent);
//
//                        // Chime
//                        try {
//                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                            r.play();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                    app.syncOrders();
//
//                } else if (messageType.equals("DrinkOfferAccepted")) {
//
//                    // When other person accept offer drink
//                    if (!app.isStaffUser() || app.isKioskMode())
//                        app.syncOrders();
//                    else
//                        app.notifyObservers(TrayApplication.ORDERS_UPDATED);
//
//                } else if (messageType.equals("DrinkOfferRejected")) {
//
//                    // When other person reject offer drink
//                    if (!app.isStaffUser() || app.isKioskMode())
//                        app.syncOrders();
//                    else
//                        app.notifyObservers(TrayApplication.ORDERS_UPDATED);
//
//                } else if (messageType.equals("message")) {
//
//                    // When other person sends the chat message
//                    app.notifyObservers(TrayApplication.MESSAGES_UPDATED);
//
//                    try {
//                        if (app.isStaffUser()) {
//
//                            // Make sure the messages activity is not already running for this sender
//                            if (app.mMessagesActivityRunning && app.selectedUserProfile != null &&
//                                    json.getString("senderId").equals(app.selectedUserProfile.getBartsyId())) {
//                                Log.v(TAG, "Messages activity already running for user " + json.getString("senderId"));
//                                return null;
//                            }
//
//                            // Message alert
//                            Intent intent = new Intent(app, CustomAlertActivity.class);
//                            intent.putExtra("title", "New chat from " + json.getString("senderNickName"));
//                            intent.putExtra("body", json.getString("message"));
//                            intent.putExtra("action", "MessagesActivity");
//                            intent.putExtra("senderId", json.getString("senderId"));
//                            intent.putExtra("receiverId", json.getString("receiverId"));
//                            intent.putExtra("senderNickName", json.getString("senderNickName"));
//                            intent.putExtra("senderImage", json.getString("senderImage"));
//                            intent.putExtra("positive", "Reply");
//                            intent.putExtra("negative", "Ignore");
//                            intent.putExtra("timeoutMillis", Constants.screenTimeoutLong);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            app.startActivity(intent);
//
//                            // Chime
//                            try {
//                                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                                r.play();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                        } else {
//                            String imagePath = json.getString("senderImage");
//                            // Download the sender's image
//                            Bitmap largeIcon = Utilities.fetchImage(app.mServerDomain + imagePath);
//                            // Generate Notification
//                            app.generateNotification("New msg from " + json.getString("body"), json.getString("message"), 0, message, largeIcon);
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else if (messageType.equals("menuUpdated")) {
//
//                    // Reload selected venue to update services
//                    app.reloadSelectedVenue();
//
//                    // Erase menu cache
//                    app.eraseCachedMenu();
//
//                    // Menus updated
//                    app.notifyObservers(TrayApplication.MENU_UPDATED);
//
//                } else if (messageType.equals("venueClosed")) {
////					app.isVenueClosed = "true";
////					app.checkOutUserFromVenue();
////					app.userCheckOut();
//                    app.notifyObservers(TrayApplication.PROFILE_UPDATED);
//                    app.notifyObservers(TrayApplication.PEOPLE_COUNT_UPDATED);
//                    app.syncOrders();
//                    app.notifyObservers(TrayApplication.VENUE_UPDATED);
//
//                } else if (messageType.equals("newPromotion")) {
//
//                    String imagePath = json.getString("venueImagePath");
//                    // Download the sender's image
//                    Bitmap largeIcon = Utilities.fetchImage(app.mServerDomain + imagePath);
//                    app.generateNotification(json.getString("venueName"), json.getString("description"), 0, message, largeIcon);
//                } else if (messageType.equals("tabOpen")) {
//
//                    // HACK - for now give server time to persist the data in the tab before pulling it
///*					try {
//                        Thread.sleep(800);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//*/
//                    Intent workIntent = new Intent(app, TabService.class);
//                    workIntent.putExtra(TabService.COMMAND, TabService.PROCESS_NEW_TAB_PN);
//                    workIntent.putExtra("tabJSON", message);
////					if (checkPN(app, json))
//                    startService(workIntent);
//
//                } else if (messageType.equals("tabUpdated")) {
//
//                    // HACK - for now give server time to persist the data in the tab before pulling it
///*					try {
//                        Thread.sleep(800);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//*/
//                    Intent workIntent = new Intent(app, TabService.class);
//                    workIntent.putExtra(TabService.COMMAND, TabService.PROCESS_TAB_UPDATED_PN);
//                    workIntent.putExtra("tabJSON", message);
////					if (checkPN(app, json))
//                    startService(workIntent);
//
//                } else if (messageType.equals("tabClosed")) {
//
//                    Intent workIntent = new Intent(app, TabService.class);
//                    workIntent.putExtra(TabService.COMMAND, TabService.PROCESS_TAB_CLOSED_PN);
//                    workIntent.putExtra("tabId", json.getString("tabId"));
////					if (checkPN(app, json))
//                    startService(workIntent);
//
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Firebase Push Notification")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
