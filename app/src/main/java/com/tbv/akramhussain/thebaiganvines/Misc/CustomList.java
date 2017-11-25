package com.tbv.akramhussain.thebaiganvines.Misc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbv.akramhussain.thebaiganvines.R;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Akram Hussain on 1/29/2017.
 */

public class CustomList extends ArrayAdapter<String> {

    private String[] titles;
    private String[] desc;
    private String[] imagepath;
    private Activity context;

   public CustomList(Activity context, String[] titles, String[] desc, String[] imagepath) {
        super(context, R.layout.listview, titles);
        this.context = context;
        this.titles = titles;
        this.desc = desc;
        this.imagepath = imagepath;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.listview, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tvMovie);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textView3);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);
        textViewName.setText(titles[position]);
        textViewDesc.setText(desc[position]);
        new DownLoadImageTask(image).execute(imagepath[position]);
        return  listViewItem;
    }
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                e.printStackTrace();
            }
            return logo;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }


}
