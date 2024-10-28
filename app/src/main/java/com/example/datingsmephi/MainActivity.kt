package com.example.datingsmephi

import DatingReminderWorker
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //WorkManager.getInstance(this).cancelAllWork()
        val uniqueWorkName = "dailyReminderWork"
        val constraints = Constraints.Builder()
            .setRequiresCharging(false) // Не требует зарядки
            .setRequiresBatteryNotLow(true) // Не запускается при низком уровне заряда
            .build()
        // Запрос на выполнение задачи каждый день
        val dailyWorkRequest = PeriodicWorkRequest.Builder(
            DatingReminderWorker::class.java,
            24, TimeUnit.HOURS // Период выполнения — каждые 24 часа
        )
            .setConstraints(constraints)
            .build()

        // Планирование задачи
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            uniqueWorkName,
            androidx.work.ExistingPeriodicWorkPolicy.KEEP, // Если работа уже существует, ничего не делаем
            dailyWorkRequest
        )

        val loginButton: Button = findViewById(R.id.loginButton)
        val registerButton: Button = findViewById(R.id.registerButton)

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            // Переход на ConfirmActivity при нажатии "Зарегистрироваться"
            val intent = Intent(this, ConfirmActivity::class.java)
            startActivity(intent)
        }
    }
}
