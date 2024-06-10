using SimpleJSON;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.Analytics;
using UnityEngine.Networking;
using UnityEngine.UI;
using static System.Net.WebRequestMethods;

[Serializable]
public class GetApiData : MonoBehaviour
{
    public string URL = "http://localhost:8080/api/photos/get-exhibition-data";
    public InputField id;
    public int exibitionId ;
    public GameObject playerStatusPanel;
    public ObjectListFromSever objectList;
    public int index = 0;
    public GameObject[] spriteArr = new GameObject[10];
    private void Start()
    {
        URL = "http://localhost:8080/api/photos/get-exhibition-data";
        GetData();
    }
    public void GetData()
    {
        StartCoroutine(FetchData());
    }
    public IEnumerator FetchData()
    {
        using (UnityWebRequest request = UnityWebRequest.Get(URL + "/" + exibitionId.ToString() /*+ "/" +  id.text*/))
        {
            // Add authorization header from postman URL
            string username = "VRTourUser";
            string password = "VRTourPassword";
            string auth = System.Convert.ToBase64String(System.Text.Encoding.GetEncoding("ISO-8859-1").GetBytes(username + ":" + password));
            request.SetRequestHeader("Authorization", "Basic " + auth);


            yield return request.SendWebRequest();
            if (request.result == UnityWebRequest.Result.ConnectionError)
            {
                Debug.Log(request.error);
            }
            else
            {

                // header = JsonUtility.FromJson<FileHeader>(request.downloadHandler.text);
                string json = request.downloadHandler.text;
                SimpleJSON.JSONNode stats = SimpleJSON.JSON.Parse(json);
                int count = stats["imagesList"].Count; // get number of elements
                ImageList[] ImgList = new ImageList[count];
                AssignData(ImgList, stats, count);

                playerStatusPanel.transform.GetChild(0).GetComponent<Text>().text = "ExibitionID: " + ImgList[index].exhibitionId.ToString();
                playerStatusPanel.transform.GetChild(1).GetComponent<Text>().text = "Filename : " + ImgList[index].fileName;
                playerStatusPanel.transform.GetChild(2).GetComponent<Text>().text = "Description : " + ImgList[index].description;
                playerStatusPanel.transform.GetChild(3).GetComponent<Text>().text = "Upload Date : " + ImgList[index].uploadDate;
                playerStatusPanel.transform.GetChild(4).GetComponent<Text>().text = "Location : " + ImgList[index].location;
                playerStatusPanel.transform.GetChild(5).GetComponent<Text>().text = "Artist : " + ImgList[index].artist;

            
                for (int i = 0; i < count; i++)
                {
                    //spriteArr[i].GetComponent<Image>().sprite = GetSpriteFromBase64(stats[i]["base64Data"]);
                    spriteArr[i].GetComponent<SpriteRenderer>().sprite = GetSpriteFromBase64(stats["imagesList"][i]["imageData"]);
                }
            }
        }
    }


    private void AssignData(ImageList[] list, SimpleJSON.JSONNode stats, int count) // data in stats index must match the name of the variables in the JSON file
    {
        for (int i = 0; i < count; i++)
        {
            ImageList temp = new ImageList();
            temp.photoId = stats["imagesList"][i]["photoId"];
            temp.exhibitionId = stats["imagesList"][i]["exhibitionId"];
            temp.fileName = stats["imagesList"][i]["fileName"];
            temp.base64Data = stats["imagesList"][i]["imageData"];
            temp.description = stats["imagesList"][i]["description"];
            temp.uploadDate = stats["imagesList"][i]["uploadDate"];
            temp.location = stats["imagesList"][i]["location"];
            temp.artist = stats["imagesList"][i]["artist"];
            list[i] = temp;
        }

    }

    private Sprite GetSpriteFromBase64(string base64Data)
    {
        Sprite sprite = null;
        byte[] imageBytes = Convert.FromBase64String(base64Data);
        Texture2D tex = new Texture2D(2, 2);
        tex.LoadImage(imageBytes);
        sprite = Sprite.Create(tex, new Rect(0.0f, 0.0f, tex.width, tex.height), new Vector2(0.5f, 0.5f), 100.0f);
        return sprite;
    }
}