function checkurl(string){
    var res = string.match(/(http(s)?:\/\/.)?(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/g);
    
    return (res !== null)
}
          $(document).ready(function(){
              $('#reservationdatetime').datetimepicker({ icons: { time: 'far fa-clock' } ,format: 'YYYY-MM-DD HH:mm:ss',
                  minDate: new Date()});
              $("#ShortLinkForm").on('submit',function(){
                  $('#expriationdate').val();
                  $("#ShortenedURL").hide();
                  $("#load-btn").hide();
                  $('#urlerrormessage').empty();
                  $('#timeerrormessage').empty();
                  $(".progress").hide();
                  $('#helpurlinput').html('');	
                  var url = $("#url").val();
                  var urllenght = url.length;
                  if ( urllenght > 1000){
                      $('#urlerrormessage').append('<br>URL must be less than 1000 chart.');
                  }else if(!checkurl(url)){
                      $('#urlerrormessage').append(' Invalid URL');
                  }else{
                      var event = moment($('#expriationdate').val()); 
              var jsonDate = event.format('YYYY-MM-DDTHH:mm:ss');
                          var timeofsubmit= new Date().getTime();
                      if(timeofsubmit> new Date($('#expriationdate').val()).getTime()){
                          $('#timeerrormessage').empty();
                          $('#timeerrormessage').append('<br> Invalid Time');              			
                      }else{
                          $.ajax({
                              xhr: function() {
                                  var xhr = new window.XMLHttpRequest();
                                  xhr.upload.addEventListener("progress", function(evt) {
                                      if (evt.lengthComputable) {
                                          var percentComplete = ((evt.loaded / evt.total) * 100);
                                          $(".progress-bar").width(Math.round(percentComplete) + '%');
                                          $(".progress").show();
                                          $(".progress-bar").html(Math.round(percentComplete)+'%');
                                      }
                                  }, false);
                                  return xhr;
                              },
                              type: "POST",
                              contentType: 'application/json',
                              data: JSON.stringify({"url": $('#url').val(),'expirationDate':jsonDate}),
                              dataType: 'json',
                              url: "/shortening",
                              success: function(data) {
                                  if(data.shortenedUrl){
                                      $("#ShortenedURL").val(window.location.href+data.shortenedUrl);
                                      $("#ShortenedURL").show();
                                      $("#load-btn").show();
                                       $('#load-btn').attr('href',$("#ShortenedURL").val());
                                  }else{
                                      alert('error');
                                  }  
                              }});
                      }
                      
                  }
                  return false;
              });
          });