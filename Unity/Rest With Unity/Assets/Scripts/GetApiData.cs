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

[Serializable]
public class GetApiData : MonoBehaviour
{
    public string URL;
    public InputField id;
    public GameObject playerStatusPanel;
    public ObjectListFromSever objectList;
    public int index = 0;
    public void GetData()
    {
        StartCoroutine(FetchData());
    }
    public IEnumerator FetchData()
    {
        using (UnityWebRequest request = UnityWebRequest.Get(URL /*+ "/" +  id.text*/))
        {
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
                int count = stats.Count; // get number of elements
                ImageList[] ImgList = new ImageList[count];
                AssignData(ImgList, stats , count);

                playerStatusPanel.transform.GetChild(0).GetComponent<Text>().text = "ExibitionID: " + ImgList[index].exhibitionId.ToString();
                playerStatusPanel.transform.GetChild(1).GetComponent<Text>().text = "Filename : " + ImgList[index].fileName;
                playerStatusPanel.transform.GetChild(2).GetComponent<Text>().text = "Description : " + ImgList[index].description;
                playerStatusPanel.transform.GetChild(3).GetComponent<Text>().text = "Upload Date : " + ImgList[index].uploadDate;
                playerStatusPanel.transform.GetChild(4).GetComponent<Text>().text = "Location : " + ImgList[index].location;
                playerStatusPanel.transform.GetChild(5).GetComponent<Text>().text = "Artist : " + ImgList[index].artist;
                
                playerStatusPanel.transform.GetChild(6).GetComponent<Image>().sprite = GetSpriteFromBase64(stats[0]["base64Data"]);
                playerStatusPanel.transform.GetChild(7).GetComponent<Image>().sprite = GetSpriteFromBase64(stats[1]["base64Data"]);
                playerStatusPanel.transform.GetChild(8).GetComponent<Image>().sprite = GetSpriteFromBase64(stats[2]["base64Data"]);
            }
        }
    }


    private void AssignData(ImageList[] list, SimpleJSON.JSONNode stats , int count) // data in stats index must match the name of the variables in the JSON file
    {
        for (int i = 0; i < count; i++)
        {
            ImageList temp = new ImageList();
            temp.photoId = stats[0]["photoId"];
            temp.exhibitionId = stats[i]["exhibitionId"];
            temp.fileName = stats[i]["fileName"];
            temp.base64Data = stats[i]["base64Data"];
            temp.description = stats[i]["description"];
            temp.uploadDate = stats[i]["uploadDate"];
            temp.location = stats[i]["location"];
            temp.artist = stats[i]["artist"];
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