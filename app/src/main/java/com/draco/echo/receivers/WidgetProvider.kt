package com.draco.echo.receivers

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.draco.echo.R
import com.draco.echo.utils.TonePlayerSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WidgetProvider : AppWidgetProvider() {
    companion object {
        const val INTENT_ACTION_PLAY_TONE = "play_tone"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        appWidgetIds.forEach {
            val intent = Intent(context, WidgetProvider::class.java)
                .setAction(INTENT_ACTION_PLAY_TONE)
            val selfPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val views = RemoteViews(
                context.packageName,
                R.layout.widget_view
            )

            views.setOnClickPendingIntent(R.id.widget, selfPendingIntent)

            appWidgetManager.updateAppWidget(it, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == INTENT_ACTION_PLAY_TONE) {
            GlobalScope.launch(Dispatchers.IO) {
                TonePlayerSingleton.play(context)
            }
        }
        super.onReceive(context, intent)
    }
}