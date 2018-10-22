function addright(key) {
  var value = $.jStorage.get(key);
  if(!value){
      // and save it
      $.jStorage.set(key, value);
  }
}

function removeright(key) {
  $.jStorage.deleteKey(key);
  listright();
  $('input[value="'+ key +'"]').prop('checked', false);
}

function clearall() {
  $.jStorage.flush();
  listright();
}

function listright() {
    var lists = $.jStorage.index();
    var shtml = '';
    var stext = '';
    var srest = '';

    if (lists.length > 0) {
      $('#selaction').show();
    } else {
      $('#selaction').hide();
    }

    for (i = 0; i < lists.length; i++) {
        shtml += '<tr>';
        shtml += '<td class="col-md-8">' + lists[i] + '</td>';
        shtml += '<td class="col-md-2"><a href="#!" onclick="removeright(\''+ lists[i] +'\')">remove</a></td>';
        shtml += '</tr>';

        // for copy function
        if (lists[i].split(' ').length > 1) {
          stext += '"'+ lists[i] + '"' + "\n";  
        } else {
          stext += lists[i] + "\n";
        }
        srest += lists[i] + ",";
    }


    $('#rightlists').empty();
    $('#rightlists').html(shtml);
    $('#textcopied').val(stext);
    $('#textrest').val(srest);
}

function selectText() {
  $('#textcopied').select();
}

function sendrecord() {
    var base_keyword = $('#base_keyword').val();
    var copied_keyword = $('#textrest').val();
    var param = "base_keyword="+ base_keyword +"&copied_keyword=" + copied_keyword;
    myajax('api/rest.php', 'POST', param);
}

function myajax(url, method, param) {
    $.ajax({
      type: method,
      url: url,
      data: param,
      beforeSend:function() {
        // this is where we append a loading image
        console.log('before sending');
      },
      success:function(data) {
        // successful request; do something with the data
          console.log(data);
      },
      error:function() {
        // failed request; give feedback to user
        console.log('request error');
      }
    });
}

$('#copyclipboard').click(function() {
  $(this).attr('data-clipboard-text', $('#textcopied').val());
  selectText();
  alert('Copied to your clipboard!');
});

$("#selectall").click(function() {
  $('input[type="checkbox"]').each(function( index ) {
      addright($(this).val());
  });
  listright();
  $('input[type="checkbox"]').prop('checked', true);
});

$("#removeall").click(function() {
  clearall();
  $('input[type="checkbox"]').prop('checked', false);
});

$(document).ready(function() {  
    clearall();
    new Clipboard('#copyclipboard');
  
//     $(".add-right").change(function() {
//         var c = $(this).val();
//         if(this.checked) {
//             addright(c);
//         } else {
//           $.jStorage.deleteKey(c);
//         }
//         listright();
//     });

    $("#textcopied").focus(function() {
        var $this = $(this);
        $this.select();

        // Work around Chrome's little problem
        $this.mouseup(function() {
            // Prevent further mouseup intervention
            $this.unbind("mouseup");
            return false;
        });
    });
    
    /* scroll to top for easy navigation */
    if ($('#back-to-top').length) {
        var scrollTrigger = 100, // px
            backToTop = function () {
                var scrollTop = $(window).scrollTop();
                if (scrollTop > scrollTrigger) {
                    $('#back-to-top').addClass('show');
                } else {
                    $('#back-to-top').removeClass('show');
                }
            };
        backToTop();
        $(window).on('scroll', function () {
            backToTop();
        });
        $('#back-to-top').on('click', function (e) {
            e.preventDefault();
            $('html,body').animate({
                scrollTop: 0
            }, 700);
        });
    }
});