package com.project.smartlog

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Log
import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder

/**
 * Created by arvin-2009 on Feb 2019.
 */
class Util {

    companion object {


        var document: Document? = null
        fun createPdf(fileName: String) {
            document = Document(PageSize.A4, 0F, 0F, 10F, 10F)
            val Root = Environment.getExternalStorageDirectory()
            Log.d("hello", Root.absolutePath)
            val Dir = File(Root.absoluteFile.toString() + "/smartlog")
            if (!Dir.exists()) {
                Log.d("hello", "doesn't exists " + Root.absolutePath)
                Dir.mkdir()
            }

            val file = File(Dir, "$fileName.pdf")
            val fileoutputstream = FileOutputStream(file)

            PdfWriter.getInstance(document, fileoutputstream)
            document!!.open()


        }

        fun addContent(content: String) {
            val p = Paragraph(content)
            document!!.add(p)
            document!!.add(Chunk(LineSeparator()));
        }

        fun endContent() {
            document!!.close()
        }

        fun openFile(activity: Activity, fileName: String) {
            // Create URI
            val file = File(
                Environment.getExternalStorageDirectory().toString() + "/" + "smartlog" + "/" + URLEncoder.encode(
                    "$fileName.pdf",
                    "UTF-8"
                )
            )//no i18n

            // Uri uri = Uri.fromFile(file);
            val uri = FileProvider.getUriForFile(
                activity!!,
                activity!!.getApplicationContext().getPackageName() + ".provider",
                file
            );//no i18n

            try {
                val intent = Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");//no i18n

//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                activity.startActivity(intent);
            } catch (e: ActivityNotFoundException) {

            }

        }

        fun permissionCheck(activity: Activity): Boolean {
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    103
                )
                return false
            }
            return true
        }
    }
}
