//function getData(){
//var out = new XMLHttpRequest();
//out.open('GET','http://192.168.43.156:8081/audio/');
//out.send();
//return out.responseText;
//}
//
//var json = JSON.parse(getData());
//console.log("Done");

var out = new XMLHttpRequest();
out.open('GET','http://192.168.43.156:8081/audio/');
out.onload=function(){
var json = JSON.parse(out.responseText);
console.log(json[6]);
var div = document.getElementById("myData");
var string = "";
for(i=0;i<json.length;i++){
string+="<p>"+json[i]+".</p>";
}

Object.entries(json).forEach((entry) =>{
const [key , value] = entry;
Object.entries(value).forEach((entry1) =>{
const [key1 , value1] = entry1;
string+="<p>"+key+'&ensp;:<br>&emsp;'+key1+'&ensp;:&ensp;'+value1+".</p>";

})});
div.insertAdjacentHTML('beforeend',string);

}
out.send();
