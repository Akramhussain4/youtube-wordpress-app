package com.tbv.akramhussain.thebaiganvines.Model;

import android.util.Log;

import com.tbv.akramhussain.thebaiganvines.Articles.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.tbv.akramhussain.thebaiganvines.R;
/**
 * Created by Hussain on 02-Jul-17.
 */

public class JSONParser {
    private static final String	TAG	= "JSONParser";
    public static ArrayList<Category> parseCategories(JSONObject jsonObject)
    {
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        try {
            JSONArray categories = jsonObject.getJSONArray("categories");
            Category all = new Category();
            all.setId(0);
            all.setName(AppController.getInstance().getString(R.string.tab_all));
            categoryArrayList.add(all);
            for (int i=0; i<categories.length(); i++)
            {
                JSONObject catObj = categories.getJSONObject(i);
                Log.d(TAG, "Parsing " + catObj.getString("title") + ", ID " + catObj.getInt("id"));
                Category c = new Category();
                c.setId(catObj.getInt("id"));
                c.setName(catObj.getString("title"));
                categoryArrayList.add(c);
            }
        }
        catch (JSONException e)
        {
            Log.d(TAG, "----------------- Json Exception");
            e.printStackTrace();
            return null;
        }

        return categoryArrayList;
    }

    public static ArrayList<Post> parsePosts(JSONObject jsonObject)
    {
        ArrayList<Post> posts = new ArrayList<>();
        try{
            JSONArray postArray = jsonObject.getJSONArray("posts");
            for (int i = 0; i < postArray.length(); i++)
            {
                JSONObject postObject = postArray.getJSONObject(i);
                Post post = new Post();
                post.setTitle(postObject.optString("title", "N/A"));
                post.setThumbnailUrl(postObject.optString("thumbnail",
                        Config.DEFAULT_THUMBNAIL_URL));
                post.setCommentCount(postObject.optInt("comment_count", 0));
                post.setDate(postObject.optString("date", "N/A"));
                post.setContent(postObject.optString("content", "N/A"));
                post.setAuthor(postObject.getJSONObject("author").optString("name", "N/A"));
                post.setId(postObject.optInt("id"));
                post.setUrl(postObject.optString("url"));
                JSONObject featuredImages = postObject.optJSONObject("thumbnail_images");
                if (featuredImages != null)
                {
                    post.setFeaturedImageUrl(featuredImages.optJSONObject("full").optString("url", Config.DEFAULT_THUMBNAIL_URL));
                }
                posts.add(post);
            }
        }
        catch (JSONException e)
        {
            Log.d(TAG, "----------------- Json Exception");
            Log.d(TAG, e.getMessage());
            return null;
        }
        return posts;
    }
}