package edu.fsu.cs.littlechef;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class UrlActivity extends AppCompatActivity {

    private EditText url;
    private Button getButton;
    private TextView result;
    private TextView result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);

        url = findViewById(R.id.url);

        result = findViewById(R.id.result);
        result2 = findViewById(R.id.result2);

        getButton = findViewById(R.id.getButton);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (url.getText().toString().contains("allrecipes.com")
                    && validURL(url.getText().toString())) {
                    getWebsite();
                }
                else {
                    Toast.makeText(UrlActivity.this, "Please Enter A Valid Url", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static boolean validURL (String url) {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                final StringBuilder builder2 = new StringBuilder();

                String temp;

                try {
                    Document doc = Jsoup.connect(url.getText().toString()).get();

                    String title = doc.title();
                    if (title.contains("- Allrecipes.com | Allrecipes"))
                    {
                        title = title.substring(0, title.length() - 36);
                    }
                    else {
                        title = title.substring(0, title.length() - 23);
                    }

                    Elements ingredients = doc.select(".checkList__line");

                    builder.append(title).append("\n");

                    // There are 2 different HTML layouts for recipes therefore we have to account
                    // for both. This web scraping only works on allrecipes.com
                    if (!ingredients.isEmpty()) {
                        for (Element ingredient : ingredients) {
                            // Added To Remove Unnecessary Element At End of .checkList__line
                            if (ingredient.toString().contains("Add all ingredients to list")) {

                            } else {
                                builder.append("\n").append(ingredient.text());
                            }
                        }
                    }
                    else
                    {
                        ingredients = doc.select(".ingredients-item-name");
                        for (Element ingredient : ingredients) {
                            // Added To Remove Unnecessary Element At End of .checkList__line
                            if (ingredient.toString().contains("Add all ingredients to list")) {

                            } else {
                                builder.append("\n").append(ingredient.text());
                            }
                        }
                    }

                    Elements steps = doc.select(".step");

                    if (!steps.isEmpty()) {
                        for (Element step : steps) {
                            // Removes Any Advertisement Text
                            temp = step.text();
                            temp = temp.replace("Advertisement", "");

                            builder2.append(temp).append("\n\n");
                        }
                    }
                    else
                    {
                        steps = doc.select(".instructions-section-item");
                        for (Element step : steps) {
                            // Removes Any Advertisement Text
                            temp = step.text();
                            temp = temp.replace("Advertisement", "");

                            // Remove Step i From Start Of Instruction
                            for (int i = 0; i < 50; i++)
                            {
                                String temp2 = "Step " + i + " ";
                                temp = temp.replace(temp2, "");
                            }

                            builder2.append("\n\n").append(temp);
                        }
                    }
                }

                catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                        result2.setText(builder2.toString());
                    }
                });
            }
        }).start();
    }
}